# A few notes about Alt-Svc

## A short hostory

Quic (which http3 is on top of) has acutally been around for a while. There are a large number of drafts with different librarys supporting different draft versions. The issues:

- Does you browser support QUIC (and http3) - If so are there command line options required to enable it
- Does your browser support the same versions of quic, and http3 as the server. (or one of the drafts instead)
- Http3 is unlike Http 1.1/2 and uses QUIC "channels" for "frames" of various types
- Http3 is like a stream therefore content-length and chunked encoding headers are not required.

## Headers

- From the http/3 rfc - regarding the client: To the client everything appears to the same. The URI (including the URL, scheme, host and port), and same x509 certificate must also be used (Similar to CNAMES).
- My conclusions about this:
  1. The only way the scheme would not change is if http 1.1/2 was already using https, otherwise upgrade it to https first.
  2. The HTTP3 server must serve the same endpoint and urls as the http 1.1/2 server
  3. The are a few was to request an upgrade to https. An easy solution would be to use a 301 redirect; however, this is often blocked by browsers.
  ```
  Strict-Transport-Security: max-age=<time>
  Upgrade-Insecure-Requests: 1
  ```
- Be sure to turn ALPN on. The ALPN protocol names are the same as those used in Alt-Svc
- The standard header `Alt-Svc` seems like it might not be supported by chrome
- `Alt-Svc` (RFC 7838) can have multiple values comma seperated and the hostname is optional.
- Example `Alt-Svc: h3="localhost:8443", quic="localhost:8443"`
- Chrome specific header: `Application-Protocol: h3,quic,h2,http/1.1`
- Possible workflow: Client connects to port 80 and receives a 301 redirect to https port 443. When they connet to `https://hostname:443/` the response then includes the `Alt-Svc` header `h3=hostname:443`, where hostname must be the same as what is on the certificate. (subject alternative name). Both tcp:443 (traditional https) and udp:443 (quic) must use the same certificate.

## Applications

Web browsers have different ways of enabling http3

1. Firefox

- Enabled by default?

2. Chrome (Desktop not mobile)

- Use `chrome://flags` and enable QUIC

3. Edge

## Netty & Vertx integration

`https://vert-x3.github.io/advanced-vertx-guide/index.html#integrating-netty`

## Current issues

- Verticles are starting but not binding to the port. Resolution: Figured out specifying dns option when creating the vertx instance solved the problem
