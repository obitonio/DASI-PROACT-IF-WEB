/**
  Fonction de connexion
*/
function connexion() {
    var champLogin = $('#champ-login').val();
    var champPassword = $('#champ-password').val();

    $('#message').html('Connexion en cours...');

    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            action: 'connecter',
            mail: champLogin,
            mdp: champPassword
        },
        dataType: 'json'
    }).done(function (data) {
        //console.log(data);

        var retour = data.retourConnexion;
        //console.log(retour);

        // si connexion ok, aller sur la page suivante :
        if (retour.localeCompare('ok_cli') == 0) {
            window.location = "interventions.html";
        }
        else if (retour.localeCompare('ok_emp') == 0) {
            window.location = "employe.html";
        }
        else {
          $("#lien-inscription").addClass('invisible');
          $('#message').html('Echec de la connexion, votre adresse email ou mot de passe est incorrect.');
          $('#message').css('display', 'block');
          $('#champ-login').addClass('is-invalid');
          $('#champ-password').addClass('is-invalid');
        }
    });
}

function verifChampsVide(champ) {
  var retour = 1;

  if ($(champ).val() === '') {
    $(champ + '-message').html('Le champ est vide.');
    $(champ + '-message').css('display', 'block');
    $(champ).addClass('is-invalid');
    retourCorrect = 0;
  }
  else {
    $(champ + '-message').css('display', 'none');
    $(champ).removeClass('is-invalid');
  }

  return retour;
}

function verifChampsInscription() {
  console.log("Verification champs");
  var retourCorrect = 1;

  // Vérification champ vide
  if ($('#champ-civilite').val() === '') {
    retourCorrect = 0;
  }

  retourCorrect = verifChampsVide('#champ-prenom');
  retourCorrect = verifChampsVide('#champ-nom');
  retourCorrect = verifChampsVide('#champ-datenaissance');
  retourCorrect = verifChampsVide('#champ-telephone');
  retourCorrect = verifChampsVide('#champ-login');
  retourCorrect = verifChampsVide('#champ-pass1');
  retourCorrect = verifChampsVide('#champ-pass2');
  retourCorrect = verifChampsVide('#champ-num');
  retourCorrect = verifChampsVide('#champ-rue');
  retourCorrect = verifChampsVide('#champ-cp');
  retourCorrect = verifChampsVide('#champ-ville');

  // Verif si mots de passe correspondent
  if ($('#champ-pass1').val() !== '' && $('#champ-pass2').val() !== '' && $('#champ-pass1').val() !== $('#champ-pass2').val()) {
    $('#champ-pass1-message').html('Les mots de passes ne correspondent pas.');
    $('#champ-pass1-message').css('display', 'block');
    $('#champ-pass1').addClass('is-invalid');
    $('#champ-pass2-message').html('Les mots de passes ne correspondent pas.');
    $('#champ-pass2-message').css('display', 'block');
    $('#champ-pass2').addClass('is-invalid');
    retourCorrect = 0;
  }

  // Vérif email
  var email = $('#champ-login').val();
  var emailRegex = /^[a-z0-9._-]+@[a-z0-9._-]+\.[a-z]{2,6}$/;
  if (email !== '' && !emailRegex.test(email)) {
    $('#champ-login-message').html('L\'adresse email est invalide. Exemple : contact@proactif.fr');
    $('#champ-login-message').css('display', 'block');
    $('#champ-login').addClass('is-invalid');
  }

  // Vérif tel
  var tel = $('#champ-telephone').val();
  var telRegex = /^(0|(00|\+)33)[67][0-9]{8}$/;
  if (tel !== '' && !telRegex.test(tel)) {
    $('#champ-telephone-message').html('Le numéro est invalide. Exemple : 0689389502');
    $('#champ-telephone-message').css('display', 'block');
    $('#champ-telephone').addClass('is-invalid');
  }

  return retourCorrect;
}
/**
  Fonction d'inscription
*/
function inscription() {
    console.log("=== Inscription ===");
    var champCivilite = $('#champ-civilite').val();
    var champPrenom = $('#champ-prenom').val();
    var champNom = $('#champ-nom').val();
    var champDateNaissance = $('#champ-datenaissance').val();
    var champTelephone = $('#champ-telephone').val();
    var champLogin = $('#champ-login').val();
    var champPassword = $('#champ-pass1').val();
    var champNumero = $('#champ-num').val();
    var champRue = $('#champ-rue').val();
    var champCodePostal = $('#champ-cp').val();
    var champVille = $('#champ-ville').val();
    var champComplement = $('#champ-complement').val();

    console.log(champDateNaissance);

    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            action: 'inscrire',
            civilite: champCivilite,
            prenom: champPrenom,
            nom: champNom,
            dateNaissance: champDateNaissance,
            telephone: champTelephone,
            mail: champLogin,
            mdp: champPassword,
            numero: champNumero,
            rue: champRue,
            codePostal: champCodePostal,
            ville: champVille,
            infosSuppAdresse: champComplement
        },
        dataType: 'json'
    }).done(function (data) {
        console.log(data);

        var retour = data.retourInscription;
        console.log(retour);

        // si connexion ok, aller sur la page suivante :
        if (retour.localeCompare('ok') == 0) {
            window.location = "interventions.html";
        }
        else if (retour.localeCompare('nok') == 0) {
          $('#message').html('Echec de la connexion, votre adresse email ou mot de passe est incorrect.');
          $('#message').css('display', 'block');
          $('#champ-login').addClass('is-invalid');
          $('#champ-password').addClass('is-invalid');
        }


    });
}

/**
  Effectuer une demande d'intervention
*/
function demanderIntervention() {
  console.log('Demander intervention');
  var champIntitule = $('#champ-intitule').val();
  var champType = $('#champ-type').val();
  var champDescription = $('#champ-description').val();
  var champType1 = '';
  var champType2 = '';
  var champType3 = '';
  var champType4 = '';

  if (champType.localeCompare('Animal') == 0) {
    champType1 = $('#champ-nom-animal').val();
    champType2 = $('#champ-type-animal').val();
  }
  else if (champType.localeCompare('Livraison') == 0) {
    champType1 = $('#champ-livraison-heure').val();
    champType2 = $('#champ-livraison-type').val();
    champType3 = $('#champ-code-suivi').val();
    champType4 = $('#champ-entreprise').val();
  }

  $.ajax({
      url: './ActionServlet',
      method: 'POST',
      data: {
          action: 'demanderIntervention',
          intitule: champIntitule,
          type: champType,
          description: champDescription,
          attributType1: champType1,
          attributType2: champType2,
          attributType3: champType3,
          attributType4: champType4
      },
      dataType: 'json'
  }).done(function (data) {
      console.log('==== Retour demanderIntervention :');
      console.log(data);

      // Reset des champs
      $('#demander-intervention').modal('toggle');

      var codeRetour = data.retourDemandeIntervention;

      if (codeRetour.localeCompare('-1') == 0) {
        $('#modalCreationInterventionEchec').modal('toggle');
      }
      else if (codeRetour.localeCompare('0') == 0) {
        $('#modalCreationInterventionEchec').modal('toggle');
      }
      else if (codeRetour.localeCompare('1') == 0) {
        $('#modalCreationInterventionSucces').modal('toggle');
      }

      obtenirInterventions();
      viderChampDemandeIntervention();
  });
}

/**
  Fonction pour obtenir les interventions
*/
function obtenirInterventions() {
  //console.log("Obtenir interventions");
    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            action: 'obtenirInterventions'
        },
        dataType: 'json'
    }).done(function (data) {
        //if (data.redirection !== null) {
        //  window.location = data.redirection;
        //}

        //console.log("==== Retour obtenirInterventions:");
        //console.log(data);
        var interventions = data.interventions;
        var contenuHtml = '';
        var lesModalHtml = '';

        // Lister les interventions et leur modal de consultation
        //console.log(interventions);
        interventions.forEach(function(inter) {
          var etat = 'En cours';
          if (inter.etat === 1)
            etat = 'Terminé';
          else if (inter.etat === -1)
            etat = 'Non résolue';

            if(data.infoUtilisateur.typeUtilisateur === 'client'){
                contenuHtml += affichageListeInterventionsClients(inter, etat);
            }
            else if(data.infoUtilisateur.typeUtilisateur === 'employe'){
                contenuHtml += affichageListeInterventionsEmployes(inter, etat);
            }
          // Création du modal pour l'intervention
          lesModalHtml += creerModalConsulterIntervention(inter, inter.infosClient, etat);
        });

        // Mettre le nom de l'utilisateur sur la barre de navigation à droite
        $('#lesInterventions').html(contenuHtml);
        $('#modals-consultation').html(lesModalHtml);

        var civiliteUtilisateur = data.infoUtilisateur.civilite;
        var prenomUtilisateur = data.infoUtilisateur.prenom;
        var nomUtilisateur = data.infoUtilisateur.nom;

        $('#utilisateur-connecte').html(civiliteUtilisateur + ' ' + prenomUtilisateur + ' ' + nomUtilisateur);

        // Remplir les infos utilisateurs pour le formulaire de demande d'intervention
        chargerUtilisateurDemandeIntervention(data.infoUtilisateur);
    });
}

function affichageListeInterventionsClients(intervention,etat) {
    var contenuHtml = '';
    contenuHtml += '<tr>';
    contenuHtml += '<td>' + intervention.date + '</td>';
    contenuHtml += '<td>' + intervention.intitule + '</td>';
    contenuHtml += '<td>' + intervention.type + '</td>';
    contenuHtml += '<td>' + intervention.employe + '</td>';
    contenuHtml += '<td>' + etat + '</td>';
    contenuHtml += '<td><button id="' + intervention.id + '" class="btn btn-info btn-sm" data-toggle="modal" data-target=".consulter-intervention-' + intervention.id + '">Consulter</button></td>';
    contenuHtml += '</tr>';
    return contenuHtml;
}

function affichageListeInterventionsEmployes(intervention,etat) {
    var contenuHtml = '';
    contenuHtml += '<tr>';
    contenuHtml += '<td>' + intervention.date + '</td>';
    contenuHtml += '<td>' + intervention.intitule + '</td>';
    contenuHtml += '<td>' + intervention.type + '</td>';
    contenuHtml += '<td>' + intervention.client + '</td>';
    contenuHtml += '<td>' + etat + '</td>';
    contenuHtml += '<td><button id="' + intervention.id + '" class="btn btn-info btn-sm" data-toggle="modal" data-target=".consulter-intervention-' + intervention.id + '">Terminer</button></td>';
    contenuHtml += '</tr>';
    return contenuHtml;
}

/**
  Charge les informations utilisateur dans le formulaire de demande d'intervention
*/
function chargerUtilisateurDemandeIntervention(unUtilisateur) {

  $('#champ-civilite').attr('placeholder', (unUtilisateur.civilite == 'M') ? 'Monsieur' : 'Madame');
  $('#champ-prenom').attr('placeholder', unUtilisateur.prenom);
  $('#champ-nom').attr('placeholder', unUtilisateur.nom);
  $('#champ-telephone').attr('placeholder', unUtilisateur.telephone);
  $('#champ-num').attr('placeholder', unUtilisateur.numeroRue);
  $('#champ-rue').attr('placeholder', unUtilisateur.rue);
  $('#champ-cp').attr('placeholder', unUtilisateur.codePostal);
  $('#champ-ville').attr('placeholder', unUtilisateur.ville);
  $('#champ-complement').attr('placeholder', unUtilisateur.complementAdresse);
}

/**
  Créer une fenêtre modale de consultation pour une intervention
*/
function creerModalConsulterIntervention(uneIntervention, unUtilisateur, unEtat) {

  var complementAdresse = (unUtilisateur.complementAdresse === undefined)? '' : unUtilisateur.complementAdresse;
  var descriptionClient = (uneIntervention.descriptionClient === undefined)? '' : uneIntervention.descriptionClient;

  var detailsType = '';
  if (uneIntervention.type.localeCompare('Animal') == 0) {
    var nomAnimal = (uneIntervention.nomAnimal === undefined)? '' : uneIntervention.nomAnimal;
    var typeAnimal = (uneIntervention.typeAnimal === undefined)? '' : uneIntervention.typeAnimal;

    detailsType = '\
    <div class="form-group">\
      <label for="champ-nom-animal">Nom</label>\
      <input  id="champ-nom-animal" type="text" class="form-control" placeholder="' + nomAnimal + '" disabled>\
    </div>\
    <div class="form-group">\
      <label for="champ-type-animal">Type (Chat, Chien ...)</label>\
      <input  id="champ-type-animal" type="text" class="form-control" placeholder="' + typeAnimal + '" disabled>\
    </div>\
    ';
  }
  else if (uneIntervention.type.localeCompare('Livraison') == 0) {
    var heureLivraison = (uneIntervention.heureLivraison === undefined)? '' : uneIntervention.heureLivraison;
    var typeLivraison = (uneIntervention.typeLivraison === undefined)? '' : uneIntervention.typeLivraison;
    var codeLivraison = (uneIntervention.codeLivraison === undefined)? '' : uneIntervention.codeLivraison;
    var entrepriseLivraison = (uneIntervention.entrepriseLivraison === undefined)? '' : uneIntervention.entrepriseLivraison;

    detailsType = '\
    <div class="row">\
      <div class="col-lg-4 col-md-4">\
        <div class="form-group">\
          <label for="champ-livraison-heure">Date et heure</label>\
          <input  id="champ-livraison-heure" type="text" class="form-control" placeholder="' + heureLivraison + '" disabled>\
        </div>\
      </div>\
      <div class="col-lg-8 col-md-8">\
        <div class="form-group">\
          <label for="champ-livraison-type">Type (Colis, lettre recommandée ...)</label>\
          <input  id="champ-livraison-type" type="text" class="form-control" placeholder="' + typeLivraison + '" disabled>\
        </div>\
      </div>\
    </div>\
    <div class="form-group">\
      <label for="champ-code-suivi">Code suivi</label>\
      <input  id="champ-code-suivi" type="text" class="form-control" placeholder="' + codeLivraison + '" disabled>\
    </div>\
    <div class="form-group">\
      <label for="champ-entreprise">Entreprise</label>\
      <input  id="champ-entreprise" type="text" class="form-control" placeholder="' + entrepriseLivraison + '" disabled>\
    </div>\
    ';
  }

    var contenuHtml = '<div class="modal fade consulter-intervention-' + uneIntervention.id + '" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">\
      <div class="modal-dialog modal-lg">\
        <div class="modal-content">\
          <div class="modal-header">\
            <h5 class="modal-title">Consulter une intervention</h5>\
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">\
              <span aria-hidden="true">&times;</span>\
            </button>\
          </div>\
          <div class="modal-body">\
            <!-- Client -->\
            <section class="margin-top-30">\
              <h2 class="text-center">Informations personnelles</h2>\
              <div class="row">\
                <div class="col-lg-2 col-md-2">\
                  <div class="form-group">\
                    <label>Civilité</label>\
                    <select class="form-control" disabled>\
                      <option>' + ((unUtilisateur.civilite == 'M') ? 'Monsieur' : 'Madame') + '</option>\
                    </select>\
                  </div>\
                </div>\
                <div class="col-lg-5 col-md-5">\
                  <div class="form-group">\
                    <label>Prénom</label>\
                    <input type="text" class="form-control" placeholder="' + unUtilisateur.prenom + '" disabled>\
                  </div>\
                </div>\
                <div class="col-lg-5 col-md-5">\
                  <div class="form-group">\
                    <label>Nom</label>\
                    <input type="text" class="form-control" placeholder="' + unUtilisateur.nom + '" disabled>\
                  </div>\
                </div>\
              </div>\
              <div class="form-group">\
                <label>Numéro de téléphone</label>\
                <input type="text" class="form-control" placeholder="' + unUtilisateur.telephone + '" disabled>\
              </div>\
              <div class="row">\
                <div class="col-lg-2 col-md-2">\
                  <div class="form-group">\
                    <label>Numéro</label>\
                    <input type="number" class="form-control" placeholder="' + unUtilisateur.numeroRue + '" disabled>\
                  </div>\
                </div>\
                <div class="col-lg-10 col-md-10">\
                  <div class="form-group">\
                    <label>Rue</label>\
                    <input type="text" class="form-control" placeholder="' + unUtilisateur.rue + '" disabled>\
                  </div>\
                </div>\
              </div>\
              <div class="row">\
                <div class="col-lg-4 col-md-4">\
                  <div class="form-group">\
                    <label>Code postal</label>\
                    <input type="text" class="form-control" placeholder="' + unUtilisateur.codePostal + '" disabled>\
                  </div>\
                </div>\
                <div class="col-lg-8 col-md-8">\
                  <div class="form-group">\
                    <label>Ville</label>\
                    <input type="text" class="form-control" placeholder="' + unUtilisateur.ville + '" disabled>\
                  </div>\
                </div>\
              </div>\
              <div class="form-group">\
                <label>Complément d\'adresse</label>\
                <textarea class="form-control" rows="3" disabled>' + complementAdresse + '</textarea>\
              </div>\
            </section>\
            <!-- Informations -->\
            <section class="margin-top-30">\
              <h2 class="text-center">Intervention</h2>\
              <div class="form-group">\
                <label>Intitulé</label>\
                <select class="form-control" disabled>\
                  <option>' + uneIntervention.intitule + '</option>\
                </select>\
              </div>\
              <div class="form-group">\
                <label>Type</label>\
                <select class="form-control" disabled>\
                  <option>' + uneIntervention.type + '</option>\
                </select>\
              </div>' + detailsType + '\
              <div class="form-group">\
                <label>Description</label>\
                <textarea class="form-control" rows="3" disabled>' + descriptionClient + '</textarea>\
              </div>\
            </section>\
            <!-- Retour intervention -->\
            <section class="margin-top-30">\
              <h2 class="text-center">Retour intervention</h2>\
              <div class="form-group">\
                <label>Etat</label>\
                <select class="form-control" disabled>\
                  <option>' + unEtat + '</option>\
                </select>\
              </div>\
              <div class="form-group">\
                <label>Commentaire</label>\
                <textarea class="form-control" rows="3" disabled></textarea>\
              </div>\
            </section>\
          </div>\
          <div class="modal-footer">\
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>\
          </div>\
        </div>\
      </div>\
    </div>\
    ';

    return contenuHtml;
}

/**
  Vide les champs de saisie d'une demande d'intervention
*/
function viderChampDemandeIntervention() {

  $('#champ-intitule').val('');
  $('#champ-type').val('Incident');
  $('#champ-description').val('');
  $('#champ-nom-animal').val('');
  $('#champ-type-animal').val('');
  $('#champ-livraison-heure').val('');
  $('#champ-livraison-type').val('');
  $('#champ-code-suivi').val('');
  $('#champ-entreprise').val('');

  $('#consulter-champs-animal').addClass('invisible');
  $('#consulter-champs-livraison').addClass('invisible');
}

// Initialize and add the map
function initMap() {
    console.log("Init Map Maggle");
     
    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            action: 'obtenirInterventions'
        },
        dataType: 'json'
    }).done(function (data){
        
              // The location of Uluru
        var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        center: new google.maps.LatLng(45.7772738, 4.8729806)
        });
        
        var interventions = data.interventions;
        interventions.forEach(function(inter) {
           console.log(inter);
           var dateTab = inter.date.split('/');
           var dateDeLIntervention = new Date(dateTab[2]+"-"+dateTab[0]+"-"+dateTab[1]);
           var dateDuJour = new Date();
           
           if((dateDeLIntervention.getDay() === dateDuJour.getDay() && dateDeLIntervention.getYear() === dateDuJour.getYear() && dateDeLIntervention.getMonth() === dateDuJour.getMonth()) && inter.etat===1){
               var marker = new google.maps.Marker({
                position: new google.maps.LatLng(inter.coordonneesLat, inter.coordonneesLng),
                map: map,
                icon: './images/greenmarker.png',
                title: inter.client});
           }
           else if (dateDeLIntervention.getDay() === dateDuJour.getDay() && dateDeLIntervention.getYear() === dateDuJour.getYear() && dateDeLIntervention.getMonth() === dateDuJour.getMonth()){
               var marker = new google.maps.Marker({
                position: new google.maps.LatLng(inter.coordonneesLat, inter.coordonneesLng),
                map: map,
                title: inter.client});
           }
           else{
               var marker = new google.maps.Marker({
                position: new google.maps.LatLng(inter.coordonneesLat, inter.coordonneesLng),
                map: map,
                icon: './images/greymarker.png',
                title: inter.client});
           }
            

        });
        });
       
}
   