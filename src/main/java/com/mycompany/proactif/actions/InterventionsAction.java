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
import com.mycompany.proactif.util.Comparateur;
import com.mycompany.proactif.util.DebugLogger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jean
 */
@WebServlet(name = "InterventionsAction", urlPatterns = {"/InterventionsAction"})
public class InterventionsAction extends HttpServlet {
    
    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        
        Utilisateur utilisateurCourant = null;
        HttpSession session = request.getSession();
        try{
            utilisateurCourant = (Utilisateur) session.getAttribute("utilisateurCourant");
        }
        catch(Exception e){
            DebugLogger.log("[InterventionsAction]", e);
        }
        String motClef = request.getParameter("motClef");
        String filtre = request.getParameter("filtre");
        
        if(utilisateurCourant instanceof Client){
            
            Client clientCourant = (Client) utilisateurCourant;
            Services.recupererToutesLesIntervention(clientCourant);
            clientCourant.setListeDesInterventions(Services.rechercher(clientCourant.getListeDesInterventions(), motClef));
            if(filtre.equals("Date")){
                Services.trierListe(clientCourant.getListeDesInterventions(), Comparateur.FILTRES.DATE, false);
            }
            else if(filtre.equals("Intitule")){
                Services.trierListe(clientCourant.getListeDesInterventions(), Comparateur.FILTRES.INTITULE, true);
            }
            
            if(motClef.length()>0){
                clientCourant.setListeDesInterventions(Services.rechercher(clientCourant.getListeDesInterventions(), motClef));
            }
            
            request.setAttribute("utilisateur", clientCourant);
            }
            else if(utilisateurCourant instanceof Employe){
                Employe employeCourant = (Employe) utilisateurCourant;
                Services.recupererToutesLesIntervention(employeCourant);
                employeCourant.setListeDesInterventions(Services.rechercher(employeCourant.getListeDesInterventions(), motClef));
                if(motClef.length()>0){
                    employeCourant.setListeDesInterventions(Services.rechercher(employeCourant.getListeDesInterventions(), motClef));
                }
                if(filtre.equals("Date")){
                Services.trierListe(employeCourant.getListeDesInterventions(), Comparateur.FILTRES.DATE, false);
                }
                else if(filtre.equals("Intitule")){
                    Services.trierListe(employeCourant.getListeDesInterventions(), Comparateur.FILTRES.INTITULE, true);
                }
                request.setAttribute("utilisateur", employeCourant);
            }
        
    }

}
