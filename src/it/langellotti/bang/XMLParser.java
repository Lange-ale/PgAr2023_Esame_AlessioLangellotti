package it.langellotti.bang;

import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

public class XMLParser {
    static final XMLInputFactory xmlif = XMLInputFactory.newInstance();

    static private String readContent(XMLStreamReader xmlr) {
        try {
            String content = xmlr.getElementText().strip();
            return content.replaceAll("\\\\n", "\n");
        } catch (Exception e) {
            Utils.printErrorAndExit(e);
            return null;
        }
    }

    static public ArrayList<Weapon> parseWeapons(String xmlFilePath) {
        var weapons = new ArrayList<Weapon>();
        String name = null, value = null, seed = null;
        int distance = 0;
        try (var inputStream = new FileInputStream(xmlFilePath)) {
            var xmlr = xmlif.createXMLStreamReader(inputStream);
            while (xmlr.hasNext()) {
                xmlr.next();
                var eventType = xmlr.getEventType();
                if (eventType == XMLStreamReader.START_ELEMENT) {
                    switch (xmlr.getLocalName()) {
                        case "nome":
                            name = readContent(xmlr);
                            break;
                        case "distanza":
                            distance = Integer.parseInt(readContent(xmlr));
                            break;
                        case "valore":
                            value = readContent(xmlr);
                            break;
                        case "seme":
                            seed = readContent(xmlr);
                            weapons.add(new Weapon(name, false, distance, value, seed));
                            break;
                    }
                }
            }
        } catch (Exception e) {
            Utils.printErrorAndExit(e);
        }
        return weapons;
    }

    public static ArrayList<Card> parseCards(String xmlFilePath) {
        var cards = new ArrayList<Card>();
        String name = null, description = null, value = null, seed = null;
        boolean equipable = false;
        try (var inputStream = new FileInputStream(xmlFilePath)) {
            var xmlr = xmlif.createXMLStreamReader(inputStream);
            while (xmlr.hasNext()) {
                xmlr.next();
                var eventType = xmlr.getEventType();
                if (eventType == XMLStreamReader.START_ELEMENT) {
                    if (xmlr.getAttributeCount() > 0)
                        equipable = xmlr.getAttributeValue(0).equals("true");
                    switch (xmlr.getLocalName()) {
                        case "nome":
                            name = readContent(xmlr);
                            break;
                        case "descrizione":
                            description = readContent(xmlr);
                            break;
                        case "valore":
                            value = readContent(xmlr);
                            break;
                        case "seme":
                            seed = readContent(xmlr);
                            cards.add(new Card(name, description, equipable, value, seed));
                            break;
                    }
                }
            }
        } catch (Exception e) {
            Utils.printErrorAndExit(e);
        }
        return cards;
    }

}
/**
 * <?xml version="1.0" encoding="UTF-8"?>
 * <arnaldoWest>
 * <ruoli>
 * <ruolo>Sceriffo</ruolo>
 * <ruolo>Fuorilegge</ruolo>
 * <ruolo>Vice</ruolo>
 * <ruolo>Rinnegato</ruolo>
 * </ruoli>
 * <personaggi>
 * <personaggio pf="4">
 * <nome>Bart Cassidy</nome>
 * <descrizione>Ogni volta che perde un PF, pesca immediatamente una carta dal
 * mazzo.</descrizione>
 * </personaggio>
 * <personaggio pf="3">
 * <nome>El Gringo</nome>
 * <descrizione>Ogni volta che perde un PF a causa di una carta giocata da un
 * giocatore, pesca
 * una carta a caso dalla mano di quel giocatore (una per ogni punto vita
 * perso). Se quel
 * giocatore non ha carte in mano -peccato!- non pesca.</descrizione>
 * </personaggio>
 * <personaggio pf="4">
 * <nome>Jourdonnais</nome>
 * <descrizione>È come se avesse sempre in gioco un Barile; può cioè “estrarre!”
 * quando è il
 * bersaglio di un BANG!, e con Cuori è mancato. Un'eventuale Barile reale in
 * gioco si somma
 * alla sua abilità naturale, consentendogli di tentare per due volte di
 * annullare il colpo.</descrizione>
 * </personaggio>
 * <personaggio pf="3">
 * <nome>Paul Regret</nome>
 * <descrizione>È come se avesse sempre in gioco un Mustang; è visto cioè subito
 * dagli altri a
 * una distanza aumentata di 1. Un'eventuale Mustang reale in gioco si somma
 * alla sua abilità
 * naturale, aumentando in totale la distanza a cui è visto di 2.</descrizione>
 * </personaggio>
 * <personaggio pf="4">
 * <nome>Rose Doolan</nome>
 * <descrizione>È come se avesse sempre in gioco un Mirino; vede cioè subito gli
 * altri ad una
 * distanza diminuita di 1. Un eventuale Mirino reale in gioco si somma alla sua
 * abilità
 * naturale, diminuendo in totale la distanza a cui vede gli altri di
 * 2.</descrizione>
 * </personaggio>
 * <personaggio pf="4">
 * <nome>Sid Ketchum</nome>
 * <descrizione>In qualunque momento può scartare 2 carte dalla propria mano per
 * recuperare 1 PF.
 * Se vuole e gli è possibile, può anche usare più volte di seguito
 * quest'abilità. Ricordate
 * che non si possono mai avere più PF di quelli iniziali.</descrizione>
 * </personaggio>
 * <personaggio pf="4">
 * <nome>Suzy Lafayette</nome>
 * <descrizione>Non appena rimane senza carte in mano, pesca immediatamente una
 * carta dal mazzo.</descrizione>
 * </personaggio>
 * </personaggi>
 * <armi>
 * <arma>
 * <nome>Schofield</nome>
 * <distanza>2</distanza>
 * <copie totale="3">
 * <copia>
 * <valore>JACK</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>DONNA</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>RE</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * </copie>
 * </arma>
 * <arma>
 * <nome>Remington</nome>
 * <distanza>3</distanza>
 * <copie totale="1">
 * <copia>
 * <valore>RE</valore>
 * <seme>FIORI</seme>
 * </copia>
 * </copie>
 * </arma>
 * <arma>
 * <nome>Rev. Carabine</nome>
 * <distanza>4</distanza>
 * <copie totale="1">
 * <copia>
 * <valore>ASSO</valore>
 * <seme>FIORI</seme>
 * </copia>
 * </copie>
 * </arma>
 * <arma>
 * <nome>Winchester</nome>
 * <distanza>5</distanza>
 * <copie totale="1">
 * <copia>
 * <valore>8</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * </copie>
 * </arma>
 * </armi>
 * <carte>
 * <carta equipaggiabile="false">
 * <nome>BANG!</nome>
 * <descrizione>La carta BANG! è il modo principale per togliere un PF ad un
 * giocatore. Se vuoi
 * giocare una carta BANG! devi verificare:\n1. a quale distanza si trova quel
 * giocatore; e\n2.
 * se la tua arma raggiunge almeno tale distanza di fuoco.</descrizione>
 * <copie totale="25">
 * <copia>
 * <valore>ASSO</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * <copia>
 * <valore>DUE</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>TRE</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>QUATTRO</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>CINQUE</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>SEI</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>SETTE</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>OTTO</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>NOVE</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>DIECI</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>JACK</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>DONNA</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>RE</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>ASSO</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>DUE</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>TRE</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>QUATTRO</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>CINQUE</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>SEI</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>SETTE</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>OTTO</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>NOVE</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>DONNA</valore>
 * <seme>CUORI</seme>
 * </copia>
 * <copia>
 * <valore>RE</valore>
 * <seme>CUORI</seme>
 * </copia>
 * <copia>
 * <valore>ASSO</valore>
 * <seme>CUORI</seme>
 * </copia>
 * </copie>
 * </carta>
 * <carta equipaggiabile="true">
 * <nome>Barile</nome>
 * <descrizione>Questa carta ti consente di “estrarre!” quando sei il bersaglio
 * di un BANG!:\n-
 * se esce Cuori sei Mancato! (come se avessi giocato un Mancato!);\n- in caso
 * contrario, non
 * succede niente.</descrizione>
 * <copie totale="2">
 * <copia>
 * <valore>DONNA</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * <copia>
 * <valore>RE</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * </copie>
 * </carta>
 * <carta equipaggiabile="false">
 * <nome>Birra</nome>
 * <descrizione>Questa carta ti fa recuperare un PF. Non puoi mai avere più PF
 * di quelli con cui
 * hai iniziato! Non puoi giocare la Birra per far recuperare punti vita a un
 * altro
 * giocatore.\nPuoi giocare la Birra in due modi:\n1. normalmente, durante il
 * tuo turno;\n2.
 * fuori turno, ma solo se hai appena ricevuto un colpo fatale, cioè un colpo
 * che ti
 * toglierebbe l'ultimo punto vita (e non se vieni semplicemente ferito).\nLa
 * Birra non ha più
 * nessun effetto se rimangono solo 2 personaggi in gioco; in altre parole, se
 * giochi la Birra
 * non recuperi alcun punto vita.</descrizione>
 * <copie totale="6">
 * <copia>
 * <valore>SEI</valore>
 * <seme>CUORI</seme>
 * </copia>
 * <copia>
 * <valore>SETTE</valore>
 * <seme>CUORI</seme>
 * </copia>
 * <copia>
 * <valore>OTTO</valore>
 * <seme>CUORI</seme>
 * </copia>
 * <copia>
 * <valore>NOVE</valore>
 * <seme>CUORI</seme>
 * </copia>
 * <copia>
 * <valore>DIECI</valore>
 * <seme>CUORI</seme>
 * </copia>
 * <copia>
 * <valore>JACK</valore>
 * <seme>CUORI</seme>
 * </copia>
 * </copie>
 * </carta>
 * <carta equipaggiabile="false">
 * <nome>Cat Balou</nome>
 * <descrizione>Questa carta ne fa scartare una ad un qualsiasi giocatore,
 * indipendentemente
 * dalla distanza.</descrizione>
 * <copie totale="4">
 * <copia>
 * <valore>NOVE</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>DIECI</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>JACK</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>RE</valore>
 * <seme>CUORI</seme>
 * </copia>
 * </copie>
 * </carta>
 * <carta equipaggiabile="false">
 * <nome>Diligenza</nome>
 * <descrizione>Questa carta consente di pescare 2 carte dalla cima del
 * mazzo.</descrizione>
 * <copie totale="2">
 * <copia>
 * <valore>NOVE</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * <copia>
 * <valore>NOVE</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * </copie>
 * </carta>
 * <carta equipaggiabile="false">
 * <nome>Gatling</nome>
 * <descrizione>La Gatling spara un BANG! a tutti gli altri giocatori,
 * indipendentemente dalla
 * distanza. Anche se la Gatling causa un BANG! a tutti gli altri, essa non è
 * una carta BANG!
 * Al tuo turno puoi giocare quante Gatling vuoi, ma sempre una sola carta
 * BANG!.</descrizione>
 * <copie totale="1">
 * <copia>
 * <valore>DIECI</valore>
 * <seme>CUORI</seme>
 * </copia>
 * </copie>
 * </carta>
 * <carta equipaggiabile="false">
 * <nome>Mancato!</nome>
 * <descrizione>Quando sei colpito da un BANG! puoi giocare subito, quindi fuori
 * dal tuo turno,
 * un Mancato! per annullare il colpo; se non lo fai, perdi un PF. Quando
 * finisci i PF sei
 * eliminato dal gioco (a meno che non giochi immediatamente una Birra). Puoi
 * annullare solo i
 * BANG! diretti a te, non quelli diretti agli altri. La carta BANG! è comunque
 * scartata, anche
 * se annullata.</descrizione>
 * <copie totale="12">
 * <copia>
 * <valore>DUE</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * <copia>
 * <valore>TRE</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * <copia>
 * <valore>QUATTRO</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * <copia>
 * <valore>CINQUE</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * <copia>
 * <valore>SEI</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * <copia>
 * <valore>SETTE</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * <copia>
 * <valore>OTTO</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * <copia>
 * <valore>DIECI</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>JACK</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>DONNA</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>RE</valore>
 * <seme>FIORI</seme>
 * </copia>
 * <copia>
 * <valore>ASSO</valore>
 * <seme>FIORI</seme>
 * </copia>
 * </copie>
 * </carta>
 * <carta equipaggiabile="true">
 * <nome>Mirino</nome>
 * <descrizione>Quando hai in gioco un Mirino vedi gli altri giocatori a una
 * distanza diminuita
 * di 1. Gli altri invece continuano a vederti alla distanza normale. Distanze
 * inferiori a 1
 * sono considerate uguali a 1.</descrizione>
 * <copie totale="1">
 * <copia>
 * <valore>ASSO</valore>
 * <seme>PICCHE</seme>
 * </copia>
 * </copie>
 * </carta>
 * <carta equipaggiabile="true">
 * <nome>Mustang</nome>
 * <descrizione>Quando hai in gioco un Mustang sei visto dagli altri a una
 * distanza aumentata di
 * 1. Tu invece continui a vedere gli altri alla distanza normale.</descrizione>
 * <copie totale="2">
 * <copia>
 * <valore>OTTO</valore>
 * <seme>CUORI</seme>
 * </copia>
 * <copia>
 * <valore>NOVE</valore>
 * <seme>CUORI</seme>
 * </copia>
 * </copie>
 * </carta>
 * <carta equipaggiabile="false">
 * <nome>Panico!</nome>
 * <descrizione>Questa carta permette di pescare una carta da un giocatore a
 * distanza 1. Ricorda
 * che questa distanza non è modificata dalle armi, ma solo da carte tipo
 * Mustang e/o Mirino.</descrizione>
 * <copie totale="4">
 * <copia>
 * <valore>OTTO</valore>
 * <seme>QUADRI</seme>
 * </copia>
 * <copia>
 * <valore>JACK</valore>
 * <seme>CUORI</seme>
 * </copia>
 * <copia>
 * <valore>DONNA</valore>
 * <seme>CUORI</seme>
 * </copia>
 * <copia>
 * <valore>ASSO</valore>
 * <seme>CUORI</seme>
 * </copia>
 * </copie>
 * </carta>
 * <carta equipaggiabile="false">
 * <nome>Saloon</nome>
 * <descrizione>Questa carta è una Birra potenziata, infatti, il suo effetto
 * complessivo è che
 * tutti i giocatori in gioco recuperano un PF. Non puoi giocare il Saloon fuori
 * turno quando
 * stai per perdere l'ultimo punto vita: il Saloon non è una
 * Birra!</descrizione>
 * <copie totale="1">
 * <copia>
 * <valore>CINQUE</valore>
 * <seme>CUORI</seme>
 * </copia>
 * </copie>
 * </carta>
 * <carta equipaggiabile="false">
 * <nome>Wells Fargo</nome>
 * <descrizione>Questa carta consente di pescare 3 carte dalla cima del
 * mazzo.</descrizione>
 * <copie totale="1">
 * <copia>
 * <valore>TRE</valore>
 * <seme>CUORI</seme>
 * </copia>
 * </copie>
 * </carta>
 * </carte>
 * </arnaldoWest>
 */