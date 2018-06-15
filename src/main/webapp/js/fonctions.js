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
        console.log("Retour");
        console.log(data);

        var interventions = data.interventions;
        var i;
        var contenuHtml;

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
          contenuHtml += '<td><button id="' + inter.id + '" class="btn btn-info btn-sm" data-toggle="modal" data-target=".consulter-intervention">Consulter</button></td>';
          contenuHtml += '</tr>';
        }

        $('#lesInterventions').html(contenuHtml);

        // si connexion ok, aller sur la page suivante :
      /*  if (retour.localeCompare('ok') == 0) {
            //window.location = "interventions.html";
        }
        else if (retour.localeCompare('nok') == 0) {
          $('#message').html('Echec de la connexion, votre adresse email ou mot de passe est incorrect.');
          $('#message').css('display', 'block');
          $('#champ-login').addClass('is-invalid');
          $('#champ-password').addClass('is-invalid');
        }*/


    });
}