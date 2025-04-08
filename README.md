# Challenge - Readme generale

### Requirements

Implementazione del backend in Java/SpringBoot per la challenge [Backend Web Developer](https://www.lascaux.it/challenge/backend-web-developer/)

### Version 0.0.3 "gestione sale"

In questa versione e' gestita la relazione tra i film in programmazione e le relative sale, anche se non e' richiesta esplicitamente nella challenge, utilizzando le relazioni @OneToMany e @ManyToOne di JPA.

Sono stati inoltre aggiunti e/o migliorati i controlli di validazione, vedere readme del backend per i dettagli.

Per testare tali controlli di validazione, sono state ristrutturate le classi di test, sia a livello di database/entity che di servizi/business logic.

In questa versione non e' cambiato molto sul frontend, se non a livello dell'interface "film.type", che e' stata riallineata alla ProiezioneDto del backend, con un semplice cambiamento nella nomenclatura dei campi.