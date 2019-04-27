/**
 * @license
 * Copyright (c) 2014, 2019, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your application specific code will go here
 */
define(['ojs/ojcore', 'knockout', 'ojs/ojmodule-element-utils', 'ojs/ojknockout', 'ojs/ojmodule-element', 'ojs/ojrouter', 'ojs/ojarraytabledatasource', 'ojs/ojoffcanvas', 'ojs/ojbutton'],
  function(oj, ko,moduleUtils) {
     function ControllerViewModel() {
      var self = this;
      self.setConectado= function(userName){
        self.userName=userName;
        
        self.router.go("juegos");
        console.log(userName);
        conectarWebSocket();
        
      }
      function conectarWebSocket(data){
        self.ws = new WebSocket("ws://localhost:8080/gamesws");

        self.ws.onopen = function(){
          console.log("WebSocket conectado");
        }

        self.ws.onclose = function(){
          console.log("WebSocket desconectado");
        }

        self.ws.onmessage = function(event){
          console.log(event.data);
          var data=JSON.parse(event.data);
          console.log(data);
          if(data.type ="Match") {
            self.opponentUserName= (data.playerA.userName==self.userName ? data.playerB.userName: data.playerA.userName);
            self.currentPlayerUserName=data.currentPlayerUserName;
            if(data.gameName !="tictactoe"){
              window.alert("No tenemos ese juego");
              self.router.go("juegos");
            }
            console.log(self.userName);
            console.log(self.currentPlayerUserName);
            console.log(self.opponentUserName);
            self.router.go("tablero");
          }else if(data.type =="Movement"){
            self.router.currentState().dealWithMessage(data);
          }
        }

        self.ws.onerror = function(){
          console.log(event.data);
        }
      }

      // Router setup
      self.router = oj.Router.rootInstance;
      self.router.configure({
       'login': {label: 'Cuenta', isDefault: true},
       'juegos': {label: 'Lista de juegos'},
       'tablero': {label: 'Tablero'},
       'register': {label: 'Creación de cuenta'},
       'salaDeEspera' : {label: 'sala de espera'}
       
      });
      oj.Router.defaults['urlAdapter'] = new oj.Router.urlParamAdapter();

      self.moduleConfig = ko.observable({'view':[], 'viewModel':null});

      self.loadModule = function() {
        ko.computed(function() {
          var name = self.router.moduleConfig.name();
          var viewPath = 'views/' + name + '.html';
          var modelPath = 'viewModels/' + name;
          var masterPromise = Promise.all([
            moduleUtils.createView({'viewPath':viewPath}),
            moduleUtils.createViewModel({'viewModelPath':modelPath})
          ]);
          masterPromise.then(
            function(values){
              self.moduleConfig({'view':values[0],'viewModel':values[1]});
            }
          );
        });
      };

      // Navigation setup
      var navData = [
      {name: 'Cuenta', id: 'login',
       iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-chart-icon-24'},
      {name: 'Lista de juegos', id: 'juegos',
       iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-fire-icon-24'},
      {name: 'Tablero', id: 'tablero',
       iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-people-icon-24'}
      ];
      self.navDataSource = new oj.ArrayTableDataSource(navData, {idAttribute: 'id'});

      // Drawer setup
      self.toggleDrawer = function() {
        return oj.OffcanvasUtils.toggle({selector: '#navDrawer', modality: 'modal', content: '#pageContent'});
      }
      // Add a close listener so we can move focus back to the toggle button when the drawer closes
      $("#navDrawer").on("ojclose", function() { $('#drawerToggleButton').focus(); });

      // Header Setup
      self.getHeaderModel = function() {
        this.pageTitle = self.router.currentState().label;
        this.transitionCompleted = function() {
          // Adjust content padding after header bindings have been applied
          self.adjustContentPadding();
        }
        this.toggleDrawer = self.toggleDrawer;
      };

      // Method for adjusting the content area top/bottom paddings to avoid overlap with any fixed regions.
      // This method should be called whenever your fixed region height may change.  The application
      // can also adjust content paddings with css classes if the fixed region height is not changing between
      // views.
      self.adjustContentPadding = function () {
        var topElem = document.getElementsByClassName('oj-applayout-fixed-top')[0];
        var contentElem = document.getElementsByClassName('oj-applayout-content')[0];
        var bottomElem = document.getElementsByClassName('oj-applayout-fixed-bottom')[0];

        if (topElem) {
          contentElem.style.paddingTop = topElem.offsetHeight+'px';
        }
        if (bottomElem) {
          contentElem.style.paddingBottom = bottomElem.offsetHeight+'px';
        }
        // Add oj-complete marker class to signal that the content area can be unhidden.
        // See the override.css file to see when the content area is hidden.
        contentElem.classList.add('oj-complete');
      }
    }

    return new ControllerViewModel();
  }
);
