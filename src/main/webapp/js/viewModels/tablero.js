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

				var coordinates = [ 9 ];
				self.userName = ko.observable(app.userName);
				self.opponentUserName = ko.observable(app.opponentUserName);
				self.currentPlayerUserName = ko
						.observable(app.currentPlayerUserName);
				self.palabraObservable = ko.observable(self.palabra);
				self.boardAux = app.boardGeneral + '';
				self.boardGeneral = self.boardAux.split(",");
				self.winner = 0;
				var palabrasAcertadas = 0;

				conectarWebSocket();

				self.listWordsUser = app.boardUserWords + '';
				console.log("Lista Palabras:" + self.listWordsUser)
				listWordsSplit = self.listWordsUser.split(",");

				self.comprobarPalabra = function(boton, n) {
					if (self.winner != 1) {
						if (boton == self.sigPalabra) {
							descubrirBoton(n);
							palabrasAcertadas++;
							if (palabrasAcertadas < 9) {
								self.sigPalabra = self.boardGeneral[palabrasAcertadas];
							} else {
								self.sigPalabra = "";
							}
							coordinates[n - 1] = 1;
						} else {
							ocultarBotones();
							palabrasAcertadas = 0;
							self.sigPalabra = self.boardGeneral[0];
							for (i = 0; i < 9; i++) {
								coordinates[i] = 0;
							}
						}
						document.getElementById('puntuacion').innerHTML = palabrasAcertadas;
						document.getElementById('palabra').innerHTML = self.sigPalabra;
						app.move(coordinates);
					}
				}

				self.actualizarTableroRival = function(tableroRival) {
					ones = 0;
					for (i = 0; i < 9; i++) {
						if (tableroRival[i] == 1) {
							ones = 1;
							descubrirBotonRival(i + 1);
						}
					}
					if (ones == 0)
						bloquearBotonRival();
				}

				function inicializar5segs() {
					ocultarBotones();
					self.sigPalabra = self.boardGeneral[0];
					document.getElementById('palabra').innerHTML = self.sigPalabra;

				}

				function descubrirBoton(n) {
					switch (n) {
					case 1:
						self.button1Text(self.button1Val);
						break;
					case 2:
						self.button2Text(self.button2Val);
						break;
					case 3:
						self.button3Text(self.button3Val);
						break;
					case 4:
						self.button4Text(self.button4Val);
						break;
					case 5:
						self.button5Text(self.button5Val);
						break;
					case 6:
						self.button6Text(self.button6Val);
						break;
					case 7:
						self.button7Text(self.button7Val);
						break;
					case 8:
						self.button8Text(self.button8Val);
						break;
					case 9:
						self.button9Text(self.button9Val);
						break;
					}
				}

				function descubrirBotonRival(n) {
					switch (n) {
					case 1:
						document.getElementById('button1Opponent').disabled = false;
						break;
					case 2:
						document.getElementById('button2Opponent').disabled = false;
						break;
					case 3:
						document.getElementById('button3Opponent').disabled = false;
						break;
					case 4:
						document.getElementById('button4Opponent').disabled = false;
						break;
					case 5:
						document.getElementById('button5Opponent').disabled = false;
						break;
					case 6:
						document.getElementById('button6Opponent').disabled = false;
						break;
					case 7:
						document.getElementById('button7Opponent').disabled = false;
						break;
					case 8:
						document.getElementById('button8Opponent').disabled = false;
						break;
					case 9:
						document.getElementById('button9Opponent').disabled = false;
						break;
					}
				}

				function ocultarBotones() {
					// oculta todos los botones
					self.button1Text("-");
					self.button2Text("-");
					self.button3Text("-");
					self.button4Text("-");
					self.button5Text("-");
					self.button6Text("-");
					self.button7Text("-");
					self.button8Text("-");
					self.button9Text("-");
				}

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

				self.button1Text = ko.observable(self.button1Val);
				self.button2Text = ko.observable(self.button2Val);
				self.button3Text = ko.observable(self.button3Val);
				self.button4Text = ko.observable(self.button4Val);
				self.button5Text = ko.observable(self.button5Val);
				self.button6Text = ko.observable(self.button6Val);
				self.button7Text = ko.observable(self.button7Val);
				self.button8Text = ko.observable(self.button8Val);
				self.button9Text = ko.observable(self.button9Val);

				self.button1Click = function() {
					console.log(self.button1Text() + " clicked");
					self.comprobarPalabra(self.button1Val, 1);
				}

				self.button2Click = function() {
					console.log(self.button2Text() + " clicked");
					self.comprobarPalabra(self.button2Val, 2);
				}
				self.button3Click = function() {
					console.log(self.button3Text() + " clicked");
					self.comprobarPalabra(self.button3Val, 3);
				}
				self.button4Click = function() {
					console.log(self.button4Text() + " clicked");
					self.comprobarPalabra(self.button4Val, 4);
				}
				self.button5Click = function() {
					console.log(self.button5Text() + " clicked");
					self.comprobarPalabra(self.button5Val, 5);
				}
				self.button6Click = function() {
					console.log(self.button6Text() + " clicked");
					self.comprobarPalabra(self.button6Val, 6);
				}
				self.button7Click = function() {
					console.log(self.button7Text() + " clicked");
					self.comprobarPalabra(self.button7Val, 7);
				}
				self.button8Click = function() {
					console.log(self.button8Text() + " clicked");
					self.comprobarPalabra(self.button8Val, 8);
				}
				self.button9Click = function() {
					console.log(self.button9Text() + " clicked");
					self.comprobarPalabra(self.button9Val, 9);
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

				}

				function conectarWebSocket(data) {
					self.ws = app.ws;

					self.ws.onopen = function() {
						console.log("[WS-Tablero] WebSocket conectado.");
					}

					self.ws.onclose = function() {
						console.log("[WS-Tablero] WebSocket desconectado.");
					}

					self.ws.onmessage = function(event) {
						console.log("[WS-Tablero] " + event.data);
						var data = JSON.parse(event.data);
						if (data.type == "Movement"
								&& data.mover == app.opponentUserName) {
							self.tableroRival = data.coordinates;
							self.actualizarTableroRival(self.tableroRival);
						}
						if (data.winnerName != null) {
							window.alert("Ha ganado " + data.winnerName)
							self.winner = 1;
						}
					}

					self.ws.onerror = function() {
						console.log("[ERROR]: " + event.data);
					}
				}

				function bloquearBotonRival() {
					document.getElementById('button1Opponent').disabled = true;
					document.getElementById('button2Opponent').disabled = true;
					document.getElementById('button3Opponent').disabled = true;
					document.getElementById('button4Opponent').disabled = true;
					document.getElementById('button5Opponent').disabled = true;
					document.getElementById('button6Opponent').disabled = true;
					document.getElementById('button7Opponent').disabled = true;
					document.getElementById('button8Opponent').disabled = true;
					document.getElementById('button9Opponent').disabled = true;
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
					self.sigPalabra = " ";
					document.getElementById('palabra').innerHTML = " ";
					document.getElementById('puntuacion').innerHTML = 0;

					bloquearBotonRival();
					setTimeout(inicializar5segs, 5000);

					for (i = 0; i < 9; i++) {
						coordinates[i] = 0;
					}
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
