/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proactif.web;

import com.mycompany.proactif.entites.Utilisateur;
import com.mycompany.proactif.services.Services;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author antoinemathat
 */
public class ConnexionAction extends HttpServlet {
    
    public void execute(HttpServletRequest request) {
        Utilisateur utilisateur = null;
        
        String mail = request.getParameter("login");
        String mdp = request.getParameter("password");
        
        utilisateur = Services.authentifier(mail, mdp);
        
        request.setAttribute("utilisateur", utilisateur);
    }
}
