/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proactif.actions;

import java.util.Date;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jean
 */
@WebServlet(name = "InscriptionAction", urlPatterns = {"/InscriptionAction"})
public class InscriptionAction extends HttpServlet {

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
        System.out.println("=== InscriptionAction ===");
            
       SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        
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
        String infosSuppAddresse = request.getParameter("infosSuppAdresse");
        
        Adresse adresseUtilisateur = new Adresse(numero, rue, codePostal, ville, infosSuppAddresse);
        Client nouveauClient = new Client(civilite,prenom, nom,format.parse(dateNaissance),telephone,mail,mdp);
        nouveauClient.setAdresse(adresseUtilisateur);
        nouveauClient = (Client) Services.creerUtilisateur(nouveauClient);
        
        HttpSession session = request.getSession();
        session.setAttribute("utilisateurCourant", nouveauClient);
        
        request.setAttribute("utilisateur", nouveauClient);
    }

}
