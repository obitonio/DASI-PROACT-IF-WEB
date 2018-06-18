/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proactif.actions;

import com.mycompany.proactif.entites.Animal;
import com.mycompany.proactif.entites.Client;
import com.mycompany.proactif.entites.Incident;
import com.mycompany.proactif.entites.Intervention;
import com.mycompany.proactif.entites.Livraison;
import com.mycompany.proactif.services.Services;
import com.mycompany.proactif.util.DebugLogger;
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
 * @author antoinemathat
 */
@WebServlet(name = "DemanderInterventionAction", urlPatterns = {"/DemanderInterventionAction"})
public class DemanderInterventionAction extends HttpServlet {

    private Intervention ne;

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
         System.out.println("=== DemanderInterventionAction ===");
         
        Client clientCourant = null;
        HttpSession session = request.getSession();
        try{
            clientCourant = (Client) session.getAttribute("utilisateurCourant");
        }
        catch(Exception e) {
            DebugLogger.log("[DemanderInterventionAction]", e);
        }
         
         String intitule = request.getParameter("intitule");
         String type = request.getParameter("type");
         String description = request.getParameter("description");
         String attributType1 = request.getParameter("attributType1");
         String attributType2 = request.getParameter("attributType2");
         String attributType3 = request.getParameter("attributType3");
         String attributType4 = request.getParameter("attributType4");
         
         Intervention demandeIntervention = null;
         
         switch (type) {
             case "Animal":
                 demandeIntervention = new Animal(intitule, description, attributType1, attributType2);
                 break;
             case "Livraison":
                 Date dateNavigateur = null;
                 try {
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
                     dateNavigateur = sdf.parse(attributType1);
                 }
                 catch (ParseException e){
                    DebugLogger.log("[DemanderInterventionAction]", e);
                 }
                 demandeIntervention = new Livraison(intitule, description, attributType1, attributType3, attributType2, attributType4);
                 ((Livraison)demandeIntervention).setHeurePassage(dateNavigateur);
                 break;
             case "Incident":
                 demandeIntervention = new Incident(intitule, description);
                 break;
         }
         
         Services.RetourCreationIntervention retour = Services.creerDemandeIntervention(clientCourant, demandeIntervention);
         
         request.setAttribute("RetourCreerDemandeIntervention", retour);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
