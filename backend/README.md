# Challenge - Backend

### Requirements

Implementazione del backend in Java/SpringBoot per la challenge [Backend Web Developer](https://www.lascaux.it/challenge/backend-web-developer/)

### Version 0.0.3 "gestione sale"

In questa implementazione e' gestita la relazione tra i film in programmazione e le relative sale, anche se non e' richiesta esplicitamente nella challenge, utilizzando le relazioni @OneToMany e @ManyToOne di JPA.

Sono stati inoltre aggiunti e/o migliorati i controlli di validazione:
 - a livello delle singole entity, utilizzando @NotNull, @NotEmpty, @Min e @Max di Hibernate/Jakarta validator;
 - a livello di business logic, sono ora presenti i controlli:
 	- durata proiezioni da 1 a 3 settimane;
 	- compatibilita' tecnologia del film con quella della sala di proiezione;
 	- verifica di non sovrapposizione proiezioni, ovvero gestione delle sale eventualmente gia' occupate

Per testare tali controlli di validazione, sono state ristrutturate le classi di test, sia a livello di database/entity che di servizi/business logic.

Vedere il branch "vanilla" per una implementazione di base, che e' stata realizzata in prima battuta per ottemperare alle restrizioni sulla data di consegna (vedere "negotiating requirements", nel contesto della metodologia agile).
