/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proactif.web;

import com.mycompany.proactif.actions.ConnexionAction;
import com.mycompany.proactif.actions.DemanderInterventionAction;
import com.mycompany.proactif.actions.InscriptionAction;
import com.mycompany.proactif.actions.InterventionsAction;
import com.mycompany.proactif.actions.RechercherInterventionsAction;
import com.mycompany.proactif.actions.TerminerInterventionAction;
import com.mycompany.proactif.actions.modificationInformationClient;
import com.mycompany.proactif.dao.JpaUtil;
import com.mycompany.proactif.entites.Client;
import com.mycompany.proactif.entites.Employe;
import com.mycompany.proactif.entites.Utilisateur;
import com.mycompany.proactif.services.Services;
import com.mycompany.proactif.services.Services.RetourCreationIntervention;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author antoinemathat
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("application/json;charset=UTF-8");
        String action = request.getParameter("action");
        System.out.println("action = " + action);
        
        HttpSession maSession = request.getSession();
            if(maSession.getAttribute("utilisateurCourant")!=null){
                if(maSession.getAttribute("utilisateurCourant") instanceof Client){
                    switch (action) {

                        case "modifierUtilisateur" :
                            modificationInformationClient modifUtilAction = new modificationInformationClient();
                            modifUtilAction.processRequest(request,response);
                            try (PrintWriter out = response.getWriter()) {
                                Serialisation.EcrireModificationUtilisateur(out, (Utilisateur)request.getAttribute("utilisateur"));
                            }
                            break;

                        case "obtenirInterventions" :
                            InterventionsAction intervAction = new InterventionsAction();
                            intervAction.processRequest(request,response);
                            try (PrintWriter out = response.getWriter()) {
                                Serialisation.EcrireListeDesInterventions(out, (Utilisateur)request.getAttribute("utilisateur"));
                            }
                            break;

                        case "demanderIntervention" :
                            DemanderInterventionAction demanderIntervention = new DemanderInterventionAction();
                            demanderIntervention.processRequest(request,response);
                            try (PrintWriter out = response.getWriter()) {
                                Serialisation.EcrireRetourDemandeIntervention(out, (RetourCreationIntervention)request.getAttribute("RetourCreerDemandeIntervention"));
                            }
                            break;
                         

                        default :
                            try (PrintWriter out = response.getWriter()) {
                                Serialisation.Redirection(out, "interventions.html");
                            }
                            break;
                    }
                }
                else if(maSession.getAttribute("utilisateurCourant") instanceof Employe){

                    switch(action){
                        
                        case "obtenirInterventions" :
                            InterventionsAction intervAction = new InterventionsAction();
                            intervAction.processRequest(request,response);
                            try (PrintWriter out = response.getWriter()) {
                                Serialisation.EcrireListeDesInterventions(out, (Utilisateur)request.getAttribute("utilisateur"));
                            }
                            break;
                        
                        case "terminerIntervention" :
                            TerminerInterventionAction terminIntervAction = new TerminerInterventionAction();
                            terminIntervAction.processRequest(request,response);
                            try (PrintWriter out = response.getWriter()) {
                                Serialisation.EcrireTerminerIntervention(out, request.getAttribute("RetourTerminerIntervention").toString());
                            }
                            break;
                        
                            
                        default :
                            try (PrintWriter out = response.getWriter()) {
                                Serialisation.Redirection(out, "employe.html");
                            }
                            break;
                    }

                }
                else{
                    if(action == "deconnecter"){
                        maSession.invalidate();
                        try (PrintWriter out = response.getWriter()) {
                                Serialisation.Redirection(out, "login.html");
                            }
                    }
                }
            }
            else{
                switch (action) {
                    case "inscrire" :

                       System.out.println("Insription");
                        InscriptionAction inscAction = new InscriptionAction();
                        inscAction.processRequest(request,response);
                        try (PrintWriter out = response.getWriter()) {
                            Serialisation.EcrireInscriptionUtilisateur(out, (Utilisateur)request.getAttribute("utilisateur"));
                        }

                        break;
                    case "connecter" :

                        ConnexionAction cnxAction = new ConnexionAction();
                        cnxAction.processRequest(request,response);
                        try (PrintWriter out = response.getWriter()) {
                            Serialisation.EcrireConnexionUtilisateur(out, (Utilisateur)request.getAttribute("utilisateur"));
                        }
                        break;

                    default :
                            try (PrintWriter out = response.getWriter()) {
                                Serialisation.Redirection(out, "login.html");
                            }
                            break;
                }                 
            }                       
        }   
   

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
        JpaUtil.destroy();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
