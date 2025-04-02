# Challenge - Backend

### Requirements

Implementazione del backend in Java/SpringBoot per la challenge [Backend Web Developer](https://www.lascaux.it/challenge/backend-web-developer/)

### Version 0.0.1_vanilla

In questa implementazione di base non e' gestita la relazione tra i film in programmazione e le relative sale, che in effetti non e' richiesta esplicitamente nella challenge.

Per questo, in prima battuta, la "sala di proiezione" e' un semplice attributo dell'entity "Film".

Questa implementazione di base e' contenuta nel branch "vanilla", che viene implementato per primo, non potendo stimare il tempo necessario ad un'implementazione completa, nelle prime fasi di sviluppo, rispetto al tempo concesso per completare la challenge.

Sempre per motivi di tempo, in prima battuta:
 
 * non vengono incluse le validazioni di base sui campi (quelle con "org.hibernate.validator") e relativi test cases;
 * i test cases implementati si limitano alle restrizioni sulla durata delle programmazioni ed agli use cases ("lista film in proiezione" e "lista storico film passati");
 * non viene validata l'eventuale sovrapposizione di piu' film in una sala di proiezione, non essendo gestita la relazione tra film e sale.

Vedere il branch "gestione_sale" per una implementazione piu' completa, che sara' realizzata compatibilmente con le restrizioni sulla data di consegna (vedere "negotiating requirements", nel contesto della metodologia agile).
