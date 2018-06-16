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
        console.log(data);

        var retour = data.retourConnexion;
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
  Fonction d'inscription
*/
function inscription() {
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

    $('#message').html('Connexion en cours...');
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
  });
}

/**
  Fonction pour obtenir les interventions
*/
function obtenirInterventions() {
  console.log("Obtenir interventions");
    $.ajax({
        url: './ActionServlet',
        method: 'POST',
        data: {
            action: 'obtenirInterventions',
        },
        dataType: 'json'
    }).done(function (data) {
        console.log("==== Retour obtenirInterventions:");
        console.log(data);

        var interventions = data.interventions;
        var i;
        var contenuHtml = '';
        var lesModalHtml = '';

        // Lister les interventions et leur modal de consultation
        for (i=0; i<interventions.length; i++) {
          var inter = interventions[i];
          var etat = 'En cours';
          if (inter.etat == 1)
            etat = 'Terminé';
          else if (inter.etat == -1)
            etat = 'Non résolue';

          contenuHtml += '<tr>';
          contenuHtml += '<td>' + inter.date + '</td>';
          contenuHtml += '<td>' + inter.intitule + '</td>';
          contenuHtml += '<td>' + inter.type + '</td>';
          contenuHtml += '<td>' + inter.employe + '</td>';
          contenuHtml += '<td>' + etat + '</td>';
          contenuHtml += '<td><button id="' + inter.id + '" class="btn btn-info btn-sm" data-toggle="modal" data-target=".consulter-intervention-' + inter.id + '">Consulter</button></td>';
          contenuHtml += '</tr>';

          // Création du modal pour l'intervention
          lesModalHtml += creerModalConsulterIntervention(inter, data.infoUtilisateur, etat);
        }

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

  // TODO Enlever les undefined
  var complementAdresse = (unUtilisateur.complementAdresse === undefined)? '' : unUtilisateur.complementAdresse;
  var descriptionClient = (uneIntervention.descriptionClient === undefined)? '' : uneIntervention.descriptionClient;

  var detailsType;
  if (uneIntervention.type.localeCompare('Animal') == 0) {
    detailsType = '\
    <div class="form-group">\
      <label for="champ-nom-animal">Nom</label>\
      <input  id="champ-nom-animal" type="text" class="form-control" placeholder="' + uneIntervention.nomAnimal + '">\
    </div>\
    <div class="form-group">\
      <label for="champ-type-animal">Type (Chat, Chien ...)</label>\
      <input  id="champ-type-animal" type="text" class="form-control" placeholder="' + uneIntervention.typeAnimal + '">\
    </div>\
    ';
  }
  else if (uneIntervention.type.localeCompare('Livraison') == 0) {
    detailsType = '\
    <div class="row">\
      <div class="col-lg-4 col-md-4">\
        <div class="form-group">\
          <label for="champ-livraison-heure">Heure</label>\
          <input  id="champ-livraison-heure" type="number" class="form-control" placeholder="' + uneIntervention.heureLivraison + '">\
        </div>\
      </div>\
      <div class="col-lg-8 col-md-8">\
        <div class="form-group">\
          <label for="champ-livraison-type">Type (Colis, lettre recommandée ...)</label>\
          <input  id="champ-livraison-type" type="text" class="form-control" placeholder="' + uneIntervention.typeLivraison + '">\
        </div>\
      </div>\
    </div>\
    <div class="form-group">\
      <label for="champ-code-suivi">Code suivi</label>\
      <input  id="champ-code-suivi" type="text" class="form-control" placeholder="' + uneIntervention.codeLivraison + '">\
    </div>\
    <div class="form-group">\
      <label for="champ-entreprise">Entreprise</label>\
      <input  id="champ-entreprise" type="text" class="form-control" placeholder="' + uneIntervention.entrepriseLivraison + '">\
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
