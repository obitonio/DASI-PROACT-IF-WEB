/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proactif.actions;

import com.mycompany.proactif.entites.Client;
import com.mycompany.proactif.entites.Employe;
import com.mycompany.proactif.entites.Utilisateur;
import com.mycompany.proactif.services.Services;
import com.mycompany.proactif.util.DebugLogger;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "RechercherInterventionsAction", urlPatterns = {"/RechercherInterventionsAction"})
public class RechercherInterventionsAction extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        Utilisateur utilisateurCourant = null;
        HttpSession session = request.getSession();
        
        String motClef = request.getParameter("motClef");
        try{
            utilisateurCourant = (Utilisateur) session.getAttribute("utilisateurCourant");
        }
        catch(Exception e){
            DebugLogger.log("[InterventionsAction]", e);
        }
        if(utilisateurCourant instanceof Client){
            
            Client clientCourant = (Client) utilisateurCourant;
            Services.recupererToutesLesIntervention(clientCourant);
            clientCourant.setListeDesInterventions(Services.rechercher(clientCourant.getListeDesInterventions(), motClef));
            request.setAttribute("utilisateur", clientCourant);
        }
        else if(utilisateurCourant instanceof Employe){
            Employe employeCourant = (Employe) utilisateurCourant;
            Services.recupererToutesLesIntervention(employeCourant);
            employeCourant.setListeDesInterventions(Services.rechercher(employeCourant.getListeDesInterventions(), motClef));
            request.setAttribute("utilisateur", employeCourant);
        }  
    }


}
