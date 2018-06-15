/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proactif.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mycompany.proactif.entites.Utilisateur;
import com.mycompany.proactif.util.DebugLogger;
import java.io.PrintWriter;

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
}
