# Code Review

```bash
Factory Pattern - For client management FIX has a Socket Initiator factory that provides parameters for the following objects that are   
                  created as new objects for each new client that logs into the application (application, messageStoreFactory, settings,  
                  logFactory, messageFactory)
                  Likewise for server management on the exchange side we use the Socket Acceptor factory from FIX 
                  
Observer Pattern - Quote updates, FIX message updates
Adapter Pattern - Window close events within java swing menu, frame, or window action listeners
```
