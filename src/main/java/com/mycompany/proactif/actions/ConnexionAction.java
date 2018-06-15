/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proactif.actions;

import com.mycompany.proactif.entites.Utilisateur;
import com.mycompany.proactif.services.Services;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author antoinemathat
 */
public class ConnexionAction extends HttpServlet {
    
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        Utilisateur utilisateur = null;
        
        String mail = request.getParameter("mail");
        String mdp = request.getParameter("mdp");
        
        utilisateur = Services.authentifier(mail, mdp);
        
        if(utilisateur!=null){
            HttpSession session = request.getSession();
            session.setAttribute("utilisateurCourant", utilisateur);
        }
        request.setAttribute("utilisateur", utilisateur);
    }
}
