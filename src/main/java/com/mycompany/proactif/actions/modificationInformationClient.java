/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proactif.actions;

import com.mycompany.proactif.entites.Adresse;
import com.mycompany.proactif.entites.Client;
import com.mycompany.proactif.entites.Utilisateur;
import com.mycompany.proactif.services.Services;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jean
 */
@WebServlet(name = "InscriptionAction", urlPatterns = {"/modificationInformationClient"})
public class modificationInformationClient extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
        public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ParseException {
            
            
       SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        String mail = request.getParameter("mail");
        String mdp = request.getParameter("mdp");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String civilite = request.getParameter("civilite");
        String dateNaissance = request.getParameter("dateNaissance");
        String telephone = request.getParameter("telephone");
        
        int numero = Integer.parseInt(request.getParameter("numero"));
        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        String infosSuppAddresse = request.getParameter("infosSuppAddresse");
        
        
        Adresse adresseUitilisateur = new Adresse(numero, rue, codePostal, ville, infosSuppAddresse);
        Client clientCourant = new Client(civilite,prenom, nom,format.parse(dateNaissance),telephone,mail,mdp);
        clientCourant.setAdresse(adresseUitilisateur);
        clientCourant = (Client) Services.creerUtilisateur(clientCourant);
        
        request.setAttribute("utilisateur", clientCourant);
    }

}
