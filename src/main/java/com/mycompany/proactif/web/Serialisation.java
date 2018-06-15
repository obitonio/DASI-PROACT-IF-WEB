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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author antoinemathat
 */
public class Serialisation {
    
    public static void EcrireConnexionUtilisateur(PrintWriter out, Utilisateur utilisateur) {
        String retour = (utilisateur != null)? "ok": "nok";
  
        JsonObject reponseJson = new JsonObject();
        reponseJson.addProperty("retourConnexion", retour);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reponseJson);
        
        out.println(json);
        System.out.println("Json : " + json);
    }
    
    public static void EcrireInscriptionUtilisateur(PrintWriter out, Utilisateur utilisateur) {
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
        
        DateFormat formatDate = new SimpleDateFormat("dd MM yyyy");
        JsonArray jsonListeInterventions = new JsonArray();
        
        if(utilisateur instanceof Client){       
            Client clientCourant = (Client) utilisateur;
            for(Intervention i : clientCourant.getListeDesInterventions()){
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
            }
        }
        else if(utilisateur instanceof Employe){
            Employe employeCourant = (Employe) utilisateur;
            for(Intervention i : employeCourant.getListeDesInterventions()){

            }
        }
        JsonObject reponseJson = new JsonObject();
        reponseJson.add("interventions",jsonListeInterventions);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(reponseJson);
        
        out.println(json);
        System.out.println("Json : " + json);
    }
}
