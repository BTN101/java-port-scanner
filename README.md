
# Java Port Scanner

A multi-threaded TCP port scanner built from scratch in Java — built to 
understand socket programming, concurrency, and core networking concepts 
from first principles rather than from a tutorial.

## What it does

Given a host and a port range, the scanner attempts a TCP connection to 
each port and classifies the result:

- **Open** — connection succeeded, something is listening
- **Closed** — connection actively refused (`ConnectException`)
- **Filtered** — no response within the timeout (`SocketTimeoutException`) — 
  usually a firewall silently dropping the request
- **Error** — anything else (e.g. a UDP-only port being probed over TCP)

## How it works

`PortCheckTask` represents a single port check. It implements `Runnable`, 
holds its own host/port, and prints its own result — independently, on 
whichever thread picks it up.

`main` creates a fixed thread pool (`ExecutorService`), submits one task 
per port in the range, then waits for all tasks to finish.

## Performance

Scanning 999 ports on a local router:

| Approach | Time |
|---|---|
| Sequential (Stage 2) | 108 seconds |
| Threaded, 50 workers (Stage 3) | 2 seconds |

**~54x speedup** from threading alone, same logic.

## A real discovery along the way

Early threaded runs returned *everything* as "filtered" — including ports 
known to be open. The cause: 50 simultaneous connection attempts overwhelmed 
the router, and it couldn't respond to each one within the (short) timeout — 
even though it wasn't actively refusing anything.

This revealed a real tradeoff: **timeout values and thread count are linked**. 
A timeout fine for one request at a time can be far too short once a target 
is hit with many requests simultaneously. Increasing the timeout (and being 
mindful of thread count) resolved it.

## Results across different devices

| Target | Open ports found | Notes |
|---|---|---|
| Home router  | 53 (DNS), 80 (HTTP), 443 (HTTPS) | Standard router services |
| Phone  | None | Modern mobile OSes silently drop unsolicited connections — all results "filtered" |
| Own machine  | 135 (RPC), 445 (SMB) | Windows internals. Port 445 is notable — the vector used by WannaCry (2017) |

Port 137 (NetBIOS) returned "error" — it's a UDP service, and this scanner 
is TCP-only, demonstrating a real limitation of TCP-based scanning.

## Usage

```bash
# Edit host, startPort, endPort in main()
# Run TestPort.java
```

## ⚠️ Legal note

Only scan devices you own, or hosts that explicitly permit it 
(e.g. [scanme.nmap.org](https://scanme.nmap.org), maintained by the 
Nmap project for exactly this purpose). Scanning networks without 
permission may violate the law, including South Africa's Cybercrimes Act.
