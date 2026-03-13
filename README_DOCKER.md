# Running locally with Docker

Build and run with Docker (no local Java/Maven required):

```bash
docker build -t jobcareer:latest .
docker run --rm -p 8080:8080 -e JWT_SECRET=your-test-secret jobcareer:latest
```

Using docker-compose (optional MySQL):

```bash
export JWT_SECRET=your-test-secret
docker-compose up --build
```

Notes:
- For Render, create a Web Service with Environment = Docker so Render builds the `Dockerfile`.
- In production, set `JWT_SECRET` and any database environment variables in Render Dashboard (do not commit secrets to the repo).
