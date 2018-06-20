/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proactif.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mycompany.proactif.entites.Animal;
import com.mycompany.proactif.entites.Client;
import com.mycompany.proactif.entites.Employe;
import com.mycompany.proactif.entites.Incident;
import com.mycompany.proactif.entites.Intervention;
import com.mycompany.proactif.entites.Livraison;
import com.mycompany.proactif.entites.Utilisateur;
import com.mycompany.proactif.services.Services;
import com.mycompany.proactif.services.Services.RetourCreationIntervention;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author antoinemathat
 */
public class Serialisation {
    
    public static void EcrireConnexionUtilisateur(PrintWriter out, Utilisateur utilisateur) {
        System.out.println("=== EcrireConnexionUtilisateur ===");
        String retour = "nok";
        
        if (utilisateur != null) {
            if(utilisateur instanceof Client){
             retour = "ok_cli";
            }

            if(utilisateur instanceof Employe){
                retour = "ok_emp";
            }
        }    
  
        JsonObject reponseJson = new JsonObject();
        reponseJson.addProperty("retourConnexion", retour);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reponseJson);
        
        out.println(json);
        System.out.println("Json : " + json);
    }
    
    public static void EcrireInscriptionUtilisateur(PrintWriter out, Utilisateur utilisateur) {
        System.out.println("=== EcrireInscriptionUtilisateur ===");
        String retour = (utilisateur != null)? "ok": "nok";
  
        JsonObject reponseJson = new JsonObject();
        reponseJson.addProperty("retourInscription", retour);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reponseJson);
        
        out.println(json);
        System.out.println("Json : " + json);
    }
    
    public static void EcrireModificationUtilisateur(PrintWriter out, Utilisateur utilisateur) {
        String retour = (utilisateur != null)? "ok": "nok";
  
        JsonObject reponseJson = new JsonObject();
        reponseJson.addProperty("retourModification", retour);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reponseJson);
        
        out.println(json);
        System.out.println("Json : " + json);
    }
    
    public static void EcrireListeDesInterventions(PrintWriter out, Utilisateur utilisateur){
        
        JsonObject jsonInfosUtilisateur = new JsonObject();
        
        jsonInfosUtilisateur.addProperty("civilite", utilisateur.getCivilite());
        jsonInfosUtilisateur.addProperty("nom", utilisateur.getNom());
        jsonInfosUtilisateur.addProperty("prenom", utilisateur.getPrenom());
        jsonInfosUtilisateur.addProperty("telephone", utilisateur.getTelephone());
        
        jsonInfosUtilisateur.addProperty("numeroRue", utilisateur.getAdresse().getNumero());
        jsonInfosUtilisateur.addProperty("rue", utilisateur.getAdresse().getRue());
        jsonInfosUtilisateur.addProperty("codePostal", utilisateur.getAdresse().getCodePostal());
        jsonInfosUtilisateur.addProperty("ville", utilisateur.getAdresse().getVille());
        jsonInfosUtilisateur.addProperty("complementAdresse", utilisateur.getAdresse().getInformations());
        
        
        
        DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatDateHeurePassage = new SimpleDateFormat("dd/MM/yyyy - hh:mm");
        JsonArray jsonListeInterventions = new JsonArray();
        
        if(utilisateur instanceof Client){  
            
            Client clientCourant = (Client) utilisateur;
            
            jsonInfosUtilisateur.addProperty("typeUtilisateur", "client");
            
            for(Intervention i : clientCourant.getListeDesInterventions()){
                    System.out.println(i.toString());
                
                    JsonObject jsonIntervention = new JsonObject();
                    JsonObject jsonClient = new JsonObject();
                    
                    jsonIntervention.addProperty("id",i.getId());
                    jsonIntervention.addProperty("date",formatDate.format(i.getDateDebut()));
                    jsonIntervention.addProperty("intitule",i.getIntitule());
                    jsonIntervention.addProperty("employe", i.getEmploye().getPrenom() + " " + i.getEmploye().getNom());
                    jsonIntervention.addProperty("etat", i.getEtat());
                    jsonIntervention.addProperty("commentaireEmploye", i.getCommentaireEmploye());
                    jsonIntervention.addProperty("descriptionClient", i.getDescriptionClient());
                    
                    
                    String type = "";
                    if (i instanceof Animal) {
                       type = "Animal";
                       jsonIntervention.addProperty("nomAnimal", ((Animal) i).getNom());
                       jsonIntervention.addProperty("typeAnimal", ((Animal) i).getType());
                    }
                    else if(i instanceof Incident) {
                        type = "Incident";  
                    }
                    else if(i instanceof Livraison) {
                        type = "Livraison";
                        jsonIntervention.addProperty("heureLivraison", (formatDateHeurePassage.format( ((Livraison) i).getHeurePassage() )));
                        jsonIntervention.addProperty("typeLivraison", ((Livraison) i).getType());
                        jsonIntervention.addProperty("codeLivraison", ((Livraison) i).getCodeSuivi());
                        jsonIntervention.addProperty("entrepriseLivraison", ((Livraison) i).getEntreprise());
                    }
                    
                    
                    jsonIntervention.addProperty("type", type);
                    
                    jsonClient.addProperty("civilite",i.getClient().getCivilite());
                    jsonClient.addProperty("nom",i.getClient().getNom());
                    jsonClient.addProperty("prenom",i.getClient().getPrenom());
                    jsonClient.addProperty("telephone", i.getClient().getTelephone());
                    
                    jsonClient.addProperty("numeroRue", utilisateur.getAdresse().getNumero());
                    jsonClient.addProperty("rue", utilisateur.getAdresse().getRue());
                    jsonClient.addProperty("codePostal", utilisateur.getAdresse().getCodePostal());
                    jsonClient.addProperty("ville", utilisateur.getAdresse().getVille());
                    jsonClient.addProperty("complementAdresse", utilisateur.getAdresse().getInformations());
                    
                    jsonIntervention.add("infosClient", jsonClient);
                    jsonListeInterventions.add(jsonIntervention);
            }    
        }
        else if(utilisateur instanceof Employe){
            
            Employe employeCourant = (Employe) utilisateur;
            
            jsonInfosUtilisateur.addProperty("typeUtilisateur", "employe");
            
            for(Intervention i : employeCourant.getListeDesInterventions()){
                JsonObject jsonIntervention = new JsonObject();
                JsonObject jsonClient = new JsonObject();
                    
                    jsonIntervention.addProperty("id",i.getId());
                    jsonIntervention.addProperty("date",formatDate.format(i.getDateDebut()));
                    jsonIntervention.addProperty("intitule",i.getIntitule());
                    jsonIntervention.addProperty("client", i.getClient().getPrenom() + " " + i.getClient().getNom());
                    jsonIntervention.addProperty("etat", i.getEtat());
                    jsonIntervention.addProperty("commentaireEmploye", i.getCommentaireEmploye());
                    jsonIntervention.addProperty("descriptionClient", i.getDescriptionClient());
                    jsonIntervention.addProperty("coordonneesLat", i.getClient().getAdresse().getCoordonneesGPS().lat);
                    jsonIntervention.addProperty("coordonneesLng", i.getClient().getAdresse().getCoordonneesGPS().lng);
                    
                    jsonClient.addProperty("civilite",i.getClient().getCivilite());
                    jsonClient.addProperty("nom",i.getClient().getNom());
                    jsonClient.addProperty("prenom",i.getClient().getPrenom());
                    jsonClient.addProperty("telephone", i.getClient().getTelephone());
                    
                    jsonClient.addProperty("numeroRue", utilisateur.getAdresse().getNumero());
                    jsonClient.addProperty("rue", utilisateur.getAdresse().getRue());
                    jsonClient.addProperty("codePostal", utilisateur.getAdresse().getCodePostal());
                    jsonClient.addProperty("ville", utilisateur.getAdresse().getVille());
                    jsonClient.addProperty("complementAdresse", utilisateur.getAdresse().getInformations());
                    
                    
                    
                    
                    jsonIntervention.add("infosClient", jsonClient);
                    
                    String type = "";
                    if (i instanceof Animal) {
                       type = "Animal";
                       jsonIntervention.addProperty("nomAnimal", ((Animal) i).getNom());
                       jsonIntervention.addProperty("typeAnimal", ((Animal) i).getType());
                    }
                    else if(i instanceof Incident) {
                        type = "Incident";  
                    }
                    else if(i instanceof Livraison) {
                        type = "Livraison";
                        jsonIntervention.addProperty("heureLivraison", (formatDateHeurePassage.format( ((Livraison) i).getHeurePassage() )));
                        jsonIntervention.addProperty("typeLivraison", ((Livraison) i).getType());
                        jsonIntervention.addProperty("codeLivraison", ((Livraison) i).getCodeSuivi());
                        jsonIntervention.addProperty("entrepriseLivraison", ((Livraison) i).getEntreprise());
                    }

                    jsonIntervention.addProperty("type", type);
                    jsonListeInterventions.add(jsonIntervention);
            }
        }
        
        
        
        JsonObject reponseJson = new JsonObject();     
        
        reponseJson.add("interventions",jsonListeInterventions);
        reponseJson.add("infoUtilisateur",jsonInfosUtilisateur);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reponseJson);
        
        out.println(json);
        System.out.println("Json : " + json);
    }
    
    public static void EcrireRetourDemandeIntervention(PrintWriter out, RetourCreationIntervention retour){
        System.out.println("=== EcrireRetourDemandeIntervention ===");
        String codeRetour = "-1"; // ErreurBase
        
        if (retour == RetourCreationIntervention.AucunEmployeDisponible)
            codeRetour = "0";
        else if (retour == RetourCreationIntervention.Succes)
            codeRetour = "1";
  
        JsonObject reponseJson = new JsonObject();
        reponseJson.addProperty("retourDemandeIntervention", codeRetour);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reponseJson);
        
        out.println(json);
        System.out.println("Json : " + json); 
     }

    public static void Redirection(PrintWriter out, String redirection){
        System.out.println("=== Redirection ===");
  
        JsonObject reponseJson = new JsonObject();
        reponseJson.addProperty("redirection", redirection);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reponseJson);
        
        out.println(json);
        System.out.println("Json : " + json);
    }

    public static void EcrireTerminerIntervention(PrintWriter out, String retour){
        
        System.out.println("=== TERMINER INTERVENTION ===");
  
        JsonObject reponseJson = new JsonObject();
        reponseJson.addProperty("retour", retour);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reponseJson);
        
        out.println(json);
        System.out.println("Json : " + json);
    }
    
    public static void EcrireInformationsUtilisateur(PrintWriter out, Utilisateur utilisateur) {
        System.out.println("=== EcrireInformationsUtilisateur ===");
        
        JsonObject reponseJson = new JsonObject();
        //reponseJson.addProperty("retour", retour);
        
        reponseJson.addProperty("civilite", utilisateur.getCivilite());
        reponseJson.addProperty("prenom", utilisateur.getPrenom());
        reponseJson.addProperty("nom", utilisateur.getNom());
        reponseJson.addProperty("telephone", utilisateur.getTelephone());
        reponseJson.addProperty("dateNaissance", utilisateur.getDateNaissanceFormate());
        
        reponseJson.addProperty("numero", utilisateur.getAdresse().getNumero());
        reponseJson.addProperty("rue", utilisateur.getAdresse().getRue());
        reponseJson.addProperty("codePostal", utilisateur.getAdresse().getCodePostal());
        reponseJson.addProperty("ville", utilisateur.getAdresse().getVille());
        reponseJson.addProperty("complementAdresse", utilisateur.getAdresse().getInformations());
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reponseJson);
        
        out.println(json);
        System.out.println("Json : " + json);
    }
}
