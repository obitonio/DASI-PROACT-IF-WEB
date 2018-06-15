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
        String retour = (utilisateur != null)? "ok": "nok";
  
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
        
        DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        JsonArray jsonListeInterventions = new JsonArray();
        
        if(utilisateur instanceof Client){  
            
            Client clientCourant = (Client) utilisateur;
            
            for(Intervention i : clientCourant.getListeDesInterventions()){
                    System.out.println(i.toString());
                
                    JsonObject jsonIntervention = new JsonObject();
                    
                    jsonIntervention.addProperty("id",i.getId());
                    jsonIntervention.addProperty("date",formatDate.format(i.getDateDebut()));
                    jsonIntervention.addProperty("intitule",i.getIntitule());
                    jsonIntervention.addProperty("employe", i.getEmploye().getPrenom() + " " + i.getEmploye().getNom());
                    jsonIntervention.addProperty("etat", i.getEtat());
                    
                    String type = "";
                    if(i instanceof Animal)
                        type = "Animal";
                    else if(i instanceof Incident)
                        type = "Incident";
                    else if(i instanceof Livraison)
                        type = "Livraison";
                    jsonIntervention.addProperty("type", type);
                    
                    jsonListeInterventions.add(jsonIntervention);
            }
            
        }
        else if(utilisateur instanceof Employe){
            
            Employe employeCourant = (Employe) utilisateur;
            
            for(Intervention i : employeCourant.getListeDesInterventions()){
                JsonObject jsonIntervention = new JsonObject();
                    
                    jsonIntervention.addProperty("id",i.getId());
                    jsonIntervention.addProperty("date",formatDate.format(i.getDateDebut()));
                    jsonIntervention.addProperty("intitule",i.getIntitule());
                    jsonIntervention.addProperty("client", i.getClient().getPrenom() + " " + i.getClient().getNom());
                    jsonIntervention.addProperty("etat", i.getEtat());
                    
                    String type = "";
                    if(i instanceof Animal)
                        type = "Animal";
                    else if(i instanceof Incident)
                        type = "Incident";
                    else if(i instanceof Livraison)
                        type = "Livraison";
                    jsonIntervention.addProperty("type", type);
                    jsonListeInterventions.add(jsonIntervention);
            }
        }
        JsonObject jsonInfosClient = new JsonObject();
        
        jsonInfosClient.addProperty("civilite", utilisateur.getCivilite());
        jsonInfosClient.addProperty("nom", utilisateur.getNom());
        jsonInfosClient.addProperty("prenom", utilisateur.getPrenom());
        jsonInfosClient.addProperty("telephone", utilisateur.getTelephone());
        
        jsonInfosClient.addProperty("numeroRue", utilisateur.getAdresse().getNumero());
        jsonInfosClient.addProperty("rue", utilisateur.getAdresse().getRue());
        jsonInfosClient.addProperty("codePostal", utilisateur.getAdresse().getCodePostal());
        jsonInfosClient.addProperty("ville", utilisateur.getAdresse().getVille());
        jsonInfosClient.addProperty("colmplementAdresse", utilisateur.getAdresse().getInformations());
        
        
        JsonObject reponseJson = new JsonObject();     
        
        reponseJson.add("interventions",jsonListeInterventions);
        reponseJson.add("infoClient",jsonInfosClient);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reponseJson);
        
        out.println(json);
        System.out.println("Json : " + json);
    }
    
    
}
