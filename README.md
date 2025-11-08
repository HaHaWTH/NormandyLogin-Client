# Normandy Login

A Minecraft mod that provides secure authentication mechanism between client and server using RSA key pairs.

## Overview

Normandy Login is a client-server mod designed to enhance traditional password-based logins on offline-mode servers with a seamless and highly secure "Remember Me" feature. By using cryptographic key pairs, it allows players to automatically log in on trusted devices without repeatedly typing a password, all while ensuring top-tier security.

## Features
- Secure Authentication: Uses RSA-2048 cryptography for secure authentication between client and server
- Automatic Key Management: Automatically generates, saves, and loads RSA key pairs per player-server combination
- Challenge-Response Protocol: Implements a secure challenge-response authentication flow
- Client-side storage: Private keys are stored under client directory `./.auth-token/servers/server-identifier/uuid.json`, sensitive information never gets transmitted over network.

## Mod Dependencies

- Fabric: Fabric API, [ForgeConfigAPIPort](https://github.com/Fuzss/forgeconfigapiport)
- NeoForge: None

## FAQ
- Q: Is this safer than using a password?
- A: Yes, significantly. Your security is based on a private key file, which is much stronger than any password you can remember. It makes your account immune to common attacks like password guessing, keylogging, and phishing.

- Q: What's a "challenge-response"? Why is it important?
- A: It's a clever way to prove you have the secret key without actually showing it. The server sends you a random, one-time-use puzzle (the "challenge"). Only your specific private key can solve it correctly (the "response"). This means that even if someone intercepts the communication, the information is useless for logging in again, effectively preventing replay attacks.

- Q: What happens if someone gets my private key file (uuid.json)?
- A: If someone gains access to your computer and copies this file, they can log into your server account. Treat this file like a real-world key: keep your computer secure. If you suspect it's been stolen, log in from a trusted device using your password and use a command to unlink the compromised key.