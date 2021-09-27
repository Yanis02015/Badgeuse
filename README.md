# Badgeuse

## Explication de la partie connexion

* <h4>Véréfier que la carte arduino est connecté :</h4>

``` java
// n represente le port auquel la carte est conecté
SerialPort testDevice = SerialPort.getCommPorts()[n];
```

Si ce n'est pas le cas afficher une fenetre de dialog qui nous averti

``` java
int dialogResult = JOptionPane.showConfirmDialog(
        new JFrame(),
        messageError,
        "Erreur de connexion",
        JOptionPane.YES_NO_OPTION);
        
if (dialogResult == JOptionPane.YES_OPTION) {
    testConnection();
} else
    w.dispose();
```

* <h4>Mettre un écouteur d'évenement qui intercepte les données envoyé par la carte Arduino</h4>

``` java
public void startScan() {
    SerialPort device = SerialPort.getCommPorts()[2];
    device.openPort();
    device.addDataListener(new SerialPortDataListener() {
        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
        }

        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {
            System.out.println("Captured");
            if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                byte[] data = serialPortEvent.getReceivedData();
                String msg = new String(data);
                
                // Condition pour filtrer les données capturer
                if (msg.contains("dec: ")) {
                Toolkit.getDefaultToolkit().beep();
                
                // Selectionner le code de la carte
                msg = msg.substring(msg.indexOf("dec: ") + 6);
                
                if (employeesEntity.itExists(msg)) {
                    // SI l'employé existe, faire quelque chose avec la donnée reçu
                } else {
                    // C'est une nouvelle carte l'enregistrer dans la base de données
                }
            }
        }
    }
}
```

* <h4>La structure des tables dans la base de données</h4>
##### Table Employé _(exemple)_
id | idEmploye | nom | prenom  | fonction
------------ | ------------- | ------------- | ------------- | -------------
1 | 89 183 204 115 | IBOUDGHACENE | Salah  | PDG
2 | 34 842 198 322 | HARAOUI | Meriem  | Assistante
.. | .. | .. | ..  | ..
.. | .. | .. | ..  | ..
192 | 12 234 690 123 | NOM | Prénom  | Stagiaire

##### Table Employé _(exemple)_
id | day | start_at | end_at  | idEmploye
------------ | ------------- | ------------- | ------------- | -------------
1 | 2021-09-14 | 07:51:04 | 15:34:23  | 89 183 204 115
2 | 2021-09-15 | 07:47:32 | 16:04:56  | 89 183 204 115
2 | 2021-09-15 | 09:20:08 | 14:01:12  | 34 842 198 322
.. | .. | .. | ..  | ..
.. | .. | .. | ..  | ..
376 | 2021-09-23 | 07:50:22 | NULL  | 89 183 204 115

    Quand la valeur de 'end_at' est sur 'NULL' cela signifie que
    la personne n'est pas sortie ou elle est sortie sans se badger.

___

* <h3>La gestion des horaires</h3>
  * Si la carte existe dans la base de données des cartes _(table employe)_ :
    * Si cette même carte existe aussi dans la base de données des horaires de pointage _(table des pointage)_
      * **(1)** Et enfin si dans la derniere fois de la journée actuel qu'il s'est pointé le 'end_at' est différent de 'NULL'
      
        alors :
        
            Ajouter une ligne dans la table des pointages, avec les information suivant :
            - day : ( la journée actuelle )
            - start_at : ( l'heure à laquelle la carte à était capturer )
            - end_at : 'NULL'
            - idEmploye : ( le numéro de la carte passé ce qui permet de faire le lien avec l'employe )
      * Sinon si la derniere fois de la journée actuel qu'il s'est pointé le 'end_at' est égal à 'NULL'
      
        alors :
            
            Modifier la valeur end_at par l'heure actuel, aisi :
            - end_at : ( l'heure à laquelle la carte à était capturer )
    * Sinon si cette carte ne figure pas dans la table des horaires (pointage)
    
        alors :
  
          Ajouter une nouvelle ligne exactement comme pour l'étape **(1)**

  * Sinon si cette carte ne figure pas dans la table des cartes

    alors :

        Donner la possibilité d'ajouter une nouvelle carte avec un formulaure 
        pour enregistrer les informations personnels de l'employé
        ( Cette étape nécessite à ce que l'admin soit connecté sans quoi un dialog d'erreur sera afficher )


* <h3>Connexion client/server</h3>
#### - Se connecter à la bases de donnée
    Pour se connecter à la base de données, nous utiliserons la classe :
    Connexion.java : com.msy.badgeuse.model.Connexion

#### - Appliquer des modifications / Récupérer de données depuis la BDD
    Pour se récupérer ou modifier ou inserer de la donnée dans la base de données, nous utiliserons la classe :
    Statement une classe de l'objet Connexion

#### - La communication avec la table Employe
    Pour toutes les communication entre notre application et la table Employe
    se font à l'aide des méthodes de la classe EmployeesEntity.java

#### - La communication avec la table Pointage
    Pour toutes les communication entre notre application et la table pointage
    se font à l'aide des méthodes de la classe PointingEntity.java

#### - Les méthodes dela classe EmployeesEntity.java
```java
// Récupérer la totalité des informations de la table des Employées
public DefaultTableModel getTableModel(String[] title)

// Récupérer les informations de pointing d'un employé spécifique à l'aide de son ID
public DefaultTableModel getTableDetails(String[] title, int id)

// Ajouter une nouvelle carte à la base de données des employées
public void addNewEmployee(Employees employee)

// Récupérer les informations personnelles d'un employé spécifique à l'aide de son ID
public Employees getEmployeById(int id)

// Modifier les informations personnelles d'un employé spécifique à l'aide de son ID
public void editEmployee(Employees employees, int id)

// Vérifier l'existance d'une carte dans la base de données des employé
public boolean itExists(String idCard)
```

#### - Les méthodes dela classe PointingEntity.java
```java
// Ajouter une ligne dans la table pointage ( avec le end_at = 'NULL' )
public void addPointing(Pointing pointing) 

// Modifier la valeur de end_at d'une ligne de la table pointage qui était definit à 'NULL'
public void addPointingEnd(int id, String endAt)

// Vérifie si la carte existe dans la table des pointage
public boolean itExists(String idCard)

// Vérifier à une date donnée la derniere fois que cette employé s'est pointer 
// 'end_at' = 'NULL' ; et renvoi vrai ou faux
public boolean isNotEnded(String identifier, String date)


// Récupère l'ID de la dernière fois qu'un employé spécifique s'est pointer
public int getLastIdOf(String cardId)
```