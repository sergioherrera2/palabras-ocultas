/**
 * @license Copyright (c) 2014, 2019, Oracle and/or its affiliates. The
 *          Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your profile ViewModel code goes here
 */
define(
		[ 'ojs/ojcore', 'knockout', 'jquery', 'appController',
				'ojs/ojmodule-element-utils', 'ojs/ojbutton' ],
		function(oj, ko, $, app, moduleUtils) {

			function TableroViewModel() {
				var self = this;

				self.userName = ko.observable(app.userName);
				self.opponentUserName = ko.observable(app.opponentUserName);
				self.currentPlayerUserName = ko
						.observable(app.currentPlayerUserName);
				self.palabraObservable = ko.observable(self.palabra);
				self.boardAux = app.boardGeneral + ' ';
				self.boardGeneral = self.boardAux.split(",");
				var palabrasAcertadas = 0;
				var sigPalabra = "";

				self.listWordsUser = app.boardUserWords + ' ';
				console.log("Lista Palabras:" + self.listWordsUser)
				listWordsSplit = self.listWordsUser.split(",");

				self.comprobarPalabra = function(boton, n) {
					if (boton == sigPalabra) {
						descubrirBoton(n);
						palabrasAcertadas++;
						sigPalabra = self.boardGeneral[palabrasAcertadas];
					} else {
						ocultarBotones();
						palabrasAcertadas = 0;
						sigPalabra = self.boardGeneral[0];
					}
					document.getElementById('puntuacion').innerHTML = palabrasAcertadas;
					document.getElementById('palabra').innerHTML = sigPalabra;
				}

				function descubrirBoton(n) {
					switch (n) {
					case 1:
						// este self es el valor que corresponde al botón html,
						// pero no se actualiza al cambiarlo
						self.button1Text = self.button1Val;
						document.getElementById('button1').innerHTML = self.button1Val;
						break;
					case 2:
						self.button2Text = self.button2Val;
						document.getElementById('button2').innerHTML = self.button2Val;
						break;
					case 3:
						self.button3Text = self.button3Val;
						document.getElementById('button3').innerHTML = self.button3Val;
						break;
					case 4:
						self.button4Text = self.button4Val;
						document.getElementById('button4').innerHTML = self.button4Val;
						break;
					case 5:
						self.button5Text = self.button5Val;
						document.getElementById('button5').innerHTML = self.button5Val;
						break;
					case 6:
						self.button6Text = self.button6Val;
						document.getElementById('button6').innerHTML = self.button6Val;
						break;
					case 7:
						self.button7Text = self.button7Val;
						document.getElementById('button7').innerHTML = self.button7Val;
						break;
					case 8:
						self.button8Text = self.button8Val;
						document.getElementById('button8').innerHTML = self.button8Val;
						break;
					case 9:
						self.button9Text = self.button9Val;
						document.getElementById('button9').innerHTML = self.button9Val;
						break;
					}
				}

				function ocultarBotones() {
					// oculta todos los botones
					self.button1Text = self.button1Val;
					document.getElementById('button1').innerHTML = "-";
					self.button2Text = self.button2Val;
					document.getElementById('button2').innerHTML = "-";
					self.button3Text = self.button3Val;
					document.getElementById('button3').innerHTML = "-";
					self.button4Text = self.button4Val;
					document.getElementById('button4').innerHTML = "-";
					self.button5Text = self.button5Val;
					document.getElementById('button5').innerHTML = "-";
					self.button6Text = self.button6Val;
					document.getElementById('button6').innerHTML = "-";
					self.button7Text = self.button7Val;
					document.getElementById('button7').innerHTML = "-";
					self.button8Text = self.button8Val;
					document.getElementById('button8').innerHTML = "-";
					self.button9Text = self.button9Val;
					document.getElementById('button9').innerHTML = "-";
				}

				self.button1Text = "-";
				self.button2Text = "-";
				self.button3Text = "-";
				self.button4Text = "-";
				self.button5Text = "-";
				self.button6Text = "-";
				self.button7Text = "-";
				self.button8Text = "-";
				self.button9Text = "-";

				self.button1Val = listWordsSplit[0];
				self.button2Val = listWordsSplit[1];
				self.button3Val = listWordsSplit[2];
				self.button4Val = listWordsSplit[3];
				self.button5Val = listWordsSplit[4];
				self.button6Val = listWordsSplit[5];
				self.button7Val = listWordsSplit[6];
				self.button8Val = listWordsSplit[7];
				self.button9Val = listWordsSplit[8];

				self.listWordsOpponent = app.boardOpponentWords + ' ';
				console.log("[INFO]" + self.listWordsOpponent);
				listWordsOpponentSplit = self.listWordsOpponent.split(",");
				self.button1OpponentText = listWordsOpponentSplit[0];
				self.button2OpponentText = listWordsOpponentSplit[1];
				self.button3OpponentText = listWordsOpponentSplit[2];
				self.button4OpponentText = listWordsOpponentSplit[3];
				self.button5OpponentText = listWordsOpponentSplit[4];
				self.button6OpponentText = listWordsOpponentSplit[5];
				self.button7OpponentText = listWordsOpponentSplit[6];
				self.button8OpponentText = listWordsOpponentSplit[7];
				self.button9OpponentText = listWordsOpponentSplit[8];

				self.button1Click = function() {
					console.log(self.button1Text + " clicked");
					self.comprobarPalabra(self.button1Val, 1);
				}

				self.button2Click = function() {
					console.log(self.button2Text + " clicked");
					self.comprobarPalabra(self.button2Val, 2);
				}
				self.button3Click = function() {
					console.log(self.button3Text + " clicked");
					self.comprobarPalabra(self.button3Val, 3);
				}
				self.button4Click = function() {
					console.log(self.button4Text + " clicked");
					self.comprobarPalabra(self.button4Val, 4);
				}
				self.button5Click = function() {
					console.log(self.button5Text + " clicked");
					self.comprobarPalabra(self.button5Val, 5);
				}
				self.button6Click = function() {
					console.log(self.button6Text + " clicked");
					self.comprobarPalabra(self.button6Val, 6);
				}
				self.button7Click = function() {
					console.log(self.button7Text + " clicked");
					self.comprobarPalabra(self.button7Val, 7);
				}
				self.button8Click = function() {
					console.log(self.button8Text + " clicked");
					self.comprobarPalabra(self.button8Val, 8);
				}
				self.button9Click = function() {
					console.log(self.button9Text + " clicked");
					self.comprobarPalabra(self.butto9Val, 9);
				}

				self.button1OpponentClick = function() {
					console.log(self.button1OpponentText + " clicked");
				}

				self.button2OpponentClick = function() {
					console.log(self.button2OpponentText + " clicked");
				}
				self.button3OpponentClick = function() {
					console.log(self.button3OpponentText + " clicked");
				}
				self.button4OpponentClick = function() {
					console.log(self.button4OpponentText + " clicked");
				}
				self.button5OpponentClick = function() {
					console.log(self.button5OpponentText + " clicked");
				}
				self.button6OpponentClick = function() {
					console.log(self.button6OpponentText + " clicked");
				}
				self.button7OpponentClick = function() {
					console.log(self.button7OpponentText + " clicked");
				}
				self.button8OpponentClick = function() {
					console.log(self.button8OpponentText + " clicked");
				}
				self.button9OpponentClick = function() {
					console.log(self.button9OpponentText + " clicked");
				}

				self.dealWithMessage = function(data) {
					console.log(data);
				}

				// Header Config
				self.headerConfig = ko.observable({
					'view' : [],
					'viewModel' : null
				});
				moduleUtils.createView({
					'viewPath' : 'views/header.html'
				}).then(function(view) {
					self.headerConfig({
						'view' : view,
						'viewModel' : new app.getHeaderModel()
					})
				})

				// Below are a set of the ViewModel methods invoked by the
				// oj-module
				// component.
				// Please reference the oj-module jsDoc for additional
				// information.

				/**
				 * Optional ViewModel method invoked after the View is inserted
				 * into the document DOM. The application can put logic that
				 * requires the DOM being attached here. This method might be
				 * called multiple times - after the View is created and
				 * inserted into the DOM and after the View is reconnected after
				 * being disconnected.
				 */
				self.connected = function() {
					sigPalabra = self.boardGeneral[0];
					document.getElementById('palabra').innerHTML = sigPalabra;
					document.getElementById('puntuacion').innerHTML = 0;

					// desactivamos los botones del rival, y podemos activarlos
					// para indicar qué palabras ha acertado
					document.getElementById('button1Opponent').disabled = true;
					document.getElementById('button2Opponent').disabled = true;
					document.getElementById('button3Opponent').disabled = true;
					document.getElementById('button4Opponent').disabled = true;
					document.getElementById('button5Opponent').disabled = true;
					document.getElementById('button6Opponent').disabled = true;
					document.getElementById('button7Opponent').disabled = true;
					document.getElementById('button8Opponent').disabled = true;
					document.getElementById('button9Opponent').disabled = true;
				};

				/**
				 * Optional ViewModel method invoked after the View is
				 * disconnected from the DOM.
				 */
				self.disconnected = function() {
					// Implement if needed
				};

				/**
				 * Optional ViewModel method invoked after transition to the new
				 * View is complete. That includes any possible animation
				 * between the old and the new View.
				 */
				self.transitionCompleted = function() {
					// Implement if needed
				};
			}

			/*
			 * Returns a constructor for the ViewModel so that the ViewModel is
			 * constructed each time the view is displayed. Return an instance
			 * of the ViewModel if only one instance of the ViewModel is needed.
			 */
			return new TableroViewModel();
		});
