# **Funcionament de l'aplicació**

L'aplicació té dues funcionalitats principals:

* Agafar per pantalla les dades d'un encàrrec i generar un fitxer.
* Mostrar les dades d'un encàrrec a partir del que es llegeix d'un fitxer.

## Estructura del projecte

El projecte s'ha organitzat entorn dos paquets: un anomenat App i un altre anomenat Model.

Model: conté les classes que formen part del model de l'aplicació.
App: conté les classes que tenen la lògica de l'aplicació interaccionant amb les classes del model.

## El Main de l'Aplicació

El mètode MainMenu() mostra les tres opcions disponibles: escriure, llegir i sortir.

El mètode DemanarOpcio() dins Aplicacio és qui orquestra totes les accions, en aquest ordre:

* Obrir un bufferedReader per tal de demanar les dades per consola.
* Segons l'opció triada:
    * Si és 1, ens condueix a AfegirDadesEncarrec().
    * Si és 2, ens condueix a MostrarEncarrec().
    * Si és 3, sortim de l'aplicació.
    * Qualsevol altra opció introduïda ens conduirà un altre cop a demanar que s'introdueixi 1, 2 o 3.

## Afegir dades d'encàrrec: generació de fitxers

Amb el buffer obert es demanen les dades per generar l'encàrrec: nom del client, telèfon, data de l'encàrrec i els articles de l'encàrrec. Es podran introduir diferents articles fins que es desitgi.

A partir d'aquí fem ús de la classe UtilWriteFitxer on estan continguts els mètodes d'escriptura en diferents formats: text multilínia, csv o binari.

L'usuari haurà de triar en quin format vol escriure el fitxer.

ALTERNATIVES:
* Generar tots tres fitxers sense demanar a l'usuari que esculli un.
* Un cop s'ha generat un format demanar a l'usuari si vol generar el mateix encàrrec amb un altre format.

## Mostrar dades d'encàrrec: lectura de fitxers

Amb el buffer encara obert ens demanaran saber el format del fitxer que volem llegir i el seu nom.

L'aplicació assumeix que el fitxer a tractar es troba en el mateix directori on prèviament aquesta aplicació els ha desat (en el cas de l'exemple és C:\\Users\\accesadades\\). Es pot perfectament canviar i fer servir qualsevol altre directori esmenant la variable folder que hi ha dintre Aplicació.

De forma semblant a l'escriptura disposem de UtilReadFitxer on trobem els mètodes de lectura en dos formats: csv o binari.

## Què no fa l'aplicació (i podria fer)?

* Validar el format de dada que l'usuari introdueix per pantalla:
    * Format de dates.
    * Telèfons.

* Actualment i per simplicitat (com ja es va dir a l'enunciat) cada encàrrec genera un fitxer diferent. Però l'aplicació podria agafar diferents encàrrecs en un mateix fitxer. O afegir-los a un fitxer existent.