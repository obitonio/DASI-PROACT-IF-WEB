/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proactif.web;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mycompany.proactif.entites.Utilisateur;
import java.io.PrintWriter;

/**
 *
 * @author antoinemathat
 */
public class Serialisation {
    
    public static void EcrireUtilisateur(PrintWriter out, Utilisateur utilisateur) {
        String retour = (utilisateur != null)? "ok": "nok";
        //JsonElement retourJson = new JsonObject(retour);
  
        JsonObject jsonObjet = new JsonObject();
        //jsonObjet.add("retourConnexion", retourJson);
        
        
    }
}
