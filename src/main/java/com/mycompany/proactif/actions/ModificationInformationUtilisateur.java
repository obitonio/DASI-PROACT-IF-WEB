/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proactif.actions;

import com.mycompany.proactif.entites.Adresse;
import com.mycompany.proactif.entites.Client;
import com.mycompany.proactif.entites.Employe;
import com.mycompany.proactif.entites.Utilisateur;
import com.mycompany.proactif.services.Services;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jean
 */
@WebServlet(name = "InscriptionAction", urlPatterns = {"/modificationInformationClient"})
public class ModificationInformationUtilisateur extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
        public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        long id = Long.parseLong(request.getParameter("id"));
        String mail = request.getParameter("mail");
        String mdp = request.getParameter("mdp");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String civilite = request.getParameter("civilite");
        Date dateNaissance = format.parse(request.getParameter("dateNaissance"));
        String telephone = request.getParameter("telephone");
        
        int numero = Integer.parseInt(request.getParameter("numero"));
        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        String infosSuppAddresse = request.getParameter("infosSuppAdresse");
        
        String type = request.getParameter("typeUtilisateur");
        Utilisateur typeUtilisateur = (type.equals(Employe.class.getSimpleName().toString())) ? new Employe() : new Client();
        
        Utilisateur unUtilisateur = Services.obtenirUtilisateur(id, typeUtilisateur);
        
        unUtilisateur.setEmail(mail);
        if (!mdp.isEmpty()) {
            unUtilisateur.setMotDePasse(mdp);
        }
        unUtilisateur.setNom(nom);
        unUtilisateur.setPrenom(prenom);
        unUtilisateur.setCivilite(civilite);
        unUtilisateur.setDateNaissance(dateNaissance);
        unUtilisateur.setTelephone(telephone);
        
        Adresse adresseUtilisateur = new Adresse(numero, rue, codePostal, ville, infosSuppAddresse);
        unUtilisateur.setAdresse(adresseUtilisateur);
        
        Utilisateur utiAJour = Services.mettreAJourUtilisateur(unUtilisateur);
        
        HttpSession session = request.getSession();
        session.setAttribute("utilisateurCourant", utiAJour);
        
        request.setAttribute("utilisateur", utiAJour);
    }

}
