# COMMANDES #
JAVAC = javac
LIB = lib/flatlaf-2.6.jar:lib/mariadb-client.jar:lib/flatlaf-extras-2.6.jar:lib/svgSalamander-1.1.4.jar
# note $$ to get a single shell $
JAVAC_OPTIONS = -encoding UTF8 -d build -cp build/fr:${LIB}  -implicit:none -sourcepath src
JAVA = java
EXEC_JAR = ${JAVA} -jar

# CHEMINS RELATIFS
SRC = src/fr/iutfbleau/projetIHM2022FI2
BUILD = build/fr/iutfbleau/projetIHM2022FI2
DOC = doc/fr/iutfbleau/projetIHM2022FI2

# CHOIX NOMS
JAR_MP = projet.jar

# BUTS FACTICES #
.PHONY : run clean doc

# BUT PAR DEFAUT #
${JAR_MP} : res build ${BUILD}/Main.class
	jar -cfme ${JAR_MP} Manifest.txt fr.iutfbleau.projetIHM2022FI2.Main res -C build fr

run : ${JAR_MP}
	${EXEC_JAR} ${JAR_MP}

# AUTRE BUTS
build : 
	@mkdir build

doc :
	@javadoc -quiet -d ./doc -linkoffline 'https://docs.oracle.com/javase/8/docs/api/' 'https://docs.oracle.com/javase/8/docs/api/' -classpath ${LIB} -sourcepath src -subpackages fr 
	@echo "Finished"

clean :
	rm -rf ${BUILD}/*

cleanjar :
	rm *.jar

# REGLES DE DEPENDANCE #

## Main ##
${BUILD}/Main.class :	${SRC}/Main.java \
						${BUILD}/Constants.class \
						${BUILD}/MNP/EtudiantNP.class \
						${BUILD}/MP/AbstractChangementFactoryP.class \
						${BUILD}/MNP/AbstractChangementFactoryNP.class \
						${BUILD}/Fenetre.class 
	${JAVAC} ${JAVAC_OPTIONS} $<


${BUILD}/Fenetre.class : ${SRC}/Fenetre.java \
							${BUILD}/view/panel/PanelChoix.class \
							${BUILD}/controller/listeners/buttons/ThemeButtonListener.class \
							${BUILD}/Themes.class \
							${BUILD}/view/panel/PanelChoixEleves.class					
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/Themes.class : ${SRC}/Themes.java
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/Init.class :	${SRC}/Init.java \
						${BUILD}/interfaces/ReloadablePanel.class \
						${BUILD}/controller/listeners/panel/TabbedPaneListener.class \
						${BUILD}/controller/listeners/comboBox/MoveComboBoxListener.class \
						${BUILD}/controller/listeners/key/ResearchBarListener.class \
						${BUILD}/controller/listeners/buttons/MoveStudentListener.class \
						${BUILD}/controller/listeners/jlist/ResearchListMouseListener.class \
						${BUILD}/view/admin/MoveStudentPanel.class \
						${BUILD}/view/custom/tree/CustomJTree.class \
						${BUILD}/view/etudiant/VoirChangements.class \
						${BUILD}/view/ResearchStudents.class \
						${BUILD}/view/panel/VoirGroupes.class \
						${BUILD}/controller/listeners/jlist/PopUpStudentListener.class\
						${BUILD}/controller/listeners/buttons/Deconnexion.class \
						${BUILD}/view/custom/worker/CustomWorker.class	
	${JAVAC} ${JAVAC_OPTIONS} $<


## Constants ##

${BUILD}/Constants.class : ${SRC}/Constants.java
	${JAVAC} ${JAVAC_OPTIONS} $<

## API ##
${BUILD}/API/MonPrint.class : ${SRC}/API/MonPrint.java  
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/API/TypeGroupe.class :	${SRC}/API/TypeGroupe.java 
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/API/Groupe.class :	${SRC}/API/Groupe.java \
	  		     			${BUILD}/API/TypeGroupe.class\
			     			${BUILD}/API/MonPrint.class
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/API/Etudiant.class :	${SRC}/API/Etudiant.java \
                            	${BUILD}/API/MonPrint.class
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/API/Changement.class : ${SRC}/API/Changement.java \
	  		            		${BUILD}/API/Etudiant.class \
	  		     	    		${BUILD}/API/Groupe.class 
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/API/AbstractGroupeFactory.class :	${SRC}/API/AbstractGroupeFactory.java \
											${BUILD}/API/Groupe.class \
	  		        						${BUILD}/API/Etudiant.class 
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/API/AbstractChangementFactory.class :	${SRC}/API/AbstractChangementFactory.java \
	  		            					    ${BUILD}/API/AbstractGroupeFactory.class \
				    							${BUILD}/API/Changement.class
	${JAVAC} ${JAVAC_OPTIONS} $<

## MP ##

${BUILD}/MP/EtudiantP.class :   ${SRC}/MP/EtudiantP.java \
                            	${BUILD}/API/Etudiant.class 
	${JAVAC} ${JAVAC_OPTIONS} $<


${BUILD}/MP/GroupeP.class :     ${SRC}/MP/GroupeP.java \
								${BUILD}/API/Groupe.class \
								${BUILD}/API/TypeGroupe.class \
								${BUILD}/API/Etudiant.class 
	${JAVAC} ${JAVAC_OPTIONS} $<


${BUILD}/MP/ChangementP.class :	${SRC}/MP/ChangementP.java \
								${BUILD}/API/Changement.class \
								${BUILD}/API/Groupe.class \
								${BUILD}/API/Etudiant.class 
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/MP/AbstractGroupeFactoryP.class :	${SRC}/MP/AbstractGroupeFactoryP.java \
											${BUILD}/MP/EtudiantP.class \
											${BUILD}/MP/GroupeP.class \
											${BUILD}/API/AbstractGroupeFactory.class 
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/MP/AbstractChangementFactoryP.class :	${SRC}/MP/AbstractChangementFactoryP.java \
												${BUILD}/MP/AbstractGroupeFactoryP.class \
												${BUILD}/MP/ChangementP.class \
												${BUILD}/API/AbstractChangementFactory.class 
	${JAVAC} ${JAVAC_OPTIONS} $<


## MNP ##

${BUILD}/MNP/EtudiantNP.class : ${SRC}/MNP/EtudiantNP.java \
                              	${BUILD}/API/Etudiant.class 
	${JAVAC} ${JAVAC_OPTIONS} $<


${BUILD}/MNP/GroupeNP.class :   ${SRC}/MNP/GroupeNP.java \
                                ${BUILD}/API/Groupe.class \
			      				${BUILD}/API/TypeGroupe.class \
                                ${BUILD}/API/Etudiant.class 
	${JAVAC} ${JAVAC_OPTIONS} $<


${BUILD}/MNP/ChangementNP.class :	${SRC}/MNP/ChangementNP.java \
									${BUILD}/API/Changement.class \
                              		${BUILD}/API/Groupe.class \
                              		${BUILD}/API/Etudiant.class 
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/MNP/AbstractGroupeFactoryNP.class :	${SRC}/MNP/AbstractGroupeFactoryNP.java \
												${BUILD}/MNP/GroupeNP.class \
												${BUILD}/API/AbstractGroupeFactory.class 
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/MNP/AbstractChangementFactoryNP.class :	${SRC}/MNP/AbstractChangementFactoryNP.java \
													${BUILD}/MNP/ChangementNP.class \
													${BUILD}/MNP/AbstractGroupeFactoryNP.class \
											 		${BUILD}/API/AbstractChangementFactory.class 
	${JAVAC} ${JAVAC_OPTIONS} $<



## Controller ##

#-----------------------------------------------------------Handlers--------------------------------------------------------#

${BUILD}/controller/handlers/ListItemTransferable.class :	${SRC}/controller/handlers/ListItemTransferable.java 									
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/handlers/MyTransferHandler.class :	${SRC}/controller/handlers/MyTransferHandler.java \
														${BUILD}/controller/handlers/ListItemTransferable.class									
	${JAVAC} ${JAVAC_OPTIONS} $<

#----------------------------------------------------------Interfaces-------------------------------------------------------#

${BUILD}/interfaces/ComboBoxGroupeInterface.class :	${SRC}/interfaces/ComboBoxGroupeInterface.java 									
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/interfaces/ReloadablePanel.class :	${SRC}/interfaces/ReloadablePanel.java 									
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/interfaces/SwingWorkerInterface.class :	${SRC}/interfaces/SwingWorkerInterface.java 									
	${JAVAC} ${JAVAC_OPTIONS} $<

#----------------------------------------------------------Listeners-------------------------------------------------------#

#------------Buttons---------------------------------------------------#

${BUILD}/controller/listeners/action/AddGroupeAction.class :	${SRC}/controller/listeners/action/AddGroupeAction.java								
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/action/DeleteGroupeAction.class :	${SRC}/controller/listeners/action/DeleteGroupeAction.java								
	${JAVAC} ${JAVAC_OPTIONS} $<

#------------Buttons---------------------------------------------------#

${BUILD}/controller/listeners/buttons/ChangementButtonListener.class :	${SRC}/controller/listeners/buttons/ChangementButtonListener.java \
																		${BUILD}/view/etudiant/ChangementButton.class \
																		${BUILD}/view/etudiant/ChangementPanel.class
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/buttons/ChoixListener.class :	${SRC}/controller/listeners/buttons/ChoixListener.java 	\
															${BUILD}/Init.class 								
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/jlist/PopUpStudentButtonListener.class :   ${SRC}/controller/listeners/jlist/PopUpStudentButtonListener.java
	${JAVAC} ${JAVAC_OPTIONS} $<
	
${BUILD}/controller/listeners/buttons/CreateChangementListener.class :	${SRC}/controller/listeners/buttons/CreateChangementListener.java \
																		${BUILD}/view/etudiant/CreateChangementPanel.class 								
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/buttons/MoveStudentListener.class :	${SRC}/controller/listeners/buttons/MoveStudentListener.java \
																	${BUILD}/view/admin/MoveStudentPanel.class 									
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/buttons/Deconnexion.class :	${SRC}/controller/listeners/buttons/Deconnexion.java 					
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/buttons/ChoixEleveButton.class :	${SRC}/controller/listeners/buttons/ChoixEleveButton.java 					
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/buttons/ThemeButtonListener.class : ${SRC}/controller/listeners/buttons/ThemeButtonListener.java
	${JAVAC} ${JAVAC_OPTIONS} $<

#------------- ComboBox ------------------------------------------------#

${BUILD}/controller/listeners/comboBox/MoveComboBoxListener.class :	${SRC}/controller/listeners/comboBox/MoveComboBoxListener.java \
																	${BUILD}/interfaces/ComboBoxGroupeInterface.class								
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/comboBox/ComboJTreeListener.class :	${SRC}/controller/listeners/comboBox/ComboJTreeListener.java							
	${JAVAC} ${JAVAC_OPTIONS} $<


#-------------- JList ---------------------------------------------------#

${BUILD}/controller/listeners/jlist/ResearchListMouseListener.class :	${SRC}/controller/listeners/jlist/ResearchListMouseListener.java \
																${BUILD}/view/custom/dialog/CustomDialogEtudiant.class								
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/jlist/PopUpStudentListener.class :	${SRC}/controller/listeners/jlist/PopUpStudentListener.java \
																		${BUILD}/view/custom/popupmenu/PopUpStudent.class	
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/jlist/ChoixEleveListListener.class :	${SRC}/controller/listeners/jlist/ChoixEleveListListener.java 
	${JAVAC} ${JAVAC_OPTIONS} $<

#------------- Jtree ---------------------------------------------------#

${BUILD}/controller/listeners/jtree/GroupeTreeListener.class :	${SRC}/controller/listeners/jtree/GroupeTreeListener.java
																			
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/jtree/JTreeButtonsListener.class :	${SRC}/controller/listeners/jtree/JTreeButtonsListener.java \
																	${BUILD}/view/custom/tree/CustomJTree.class											
	${JAVAC} ${JAVAC_OPTIONS} $<


${BUILD}/controller/listeners/jtree/PopUpGroupeListener.class :	${SRC}/controller/listeners/jtree/PopUpGroupeListener.java \
																	${BUILD}/view/custom/popupmenu/PopUpGroupe.class												
	${JAVAC} ${JAVAC_OPTIONS} $<

#------------ Key -----------------------------------------------------#

${BUILD}/controller/listeners/key/ResearchBarListener.class :	${SRC}/controller/listeners/key/ResearchBarListener.java 									
	${JAVAC} ${JAVAC_OPTIONS} $<

#---------- Panel ---------------------------------------------------#

${BUILD}/controller/listeners/buttons/AddStudentListener.class :	${SRC}/controller/listeners/buttons/AddStudentListener.java \
																	${BUILD}/view/admin/AddStudentPanel.class \
																	${BUILD}/controller/handlers/MyTransferHandler.class										
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/controller/listeners/panel/SwitchAddStudentListener.class :	${SRC}/controller/listeners/panel/SwitchAddStudentListener.java \
																		${BUILD}/view/custom/tree/CustomJTree.class	\
																		${BUILD}/interfaces/SwingWorkerInterface.class						
	${JAVAC} ${JAVAC_OPTIONS} $<


${BUILD}/controller/listeners/panel/TabbedPaneListener.class :	${SRC}/controller/listeners/panel/TabbedPaneListener.java 									
	${JAVAC} ${JAVAC_OPTIONS} $<

## View ##

#---------------------------- Admin -------------------------------#

${BUILD}/view/admin/AddStudentPanel.class : ${SRC}/view/admin/AddStudentPanel.java \
											${BUILD}/controller/handlers/MyTransferHandler.class \
											${BUILD}/view/custom/renderer/CustomListRenderer.class
	${JAVAC} ${JAVAC_OPTIONS} $<


${BUILD}/view/admin/MoveStudentPanel.class : ${SRC}/view/admin/MoveStudentPanel.java \
											${BUILD}/interfaces/ComboBoxGroupeInterface.class \
											${BUILD}/controller/listeners/comboBox/MoveComboBoxListener.class \
											${BUILD}/view/custom/renderer/CustomListRenderer.class \
											${BUILD}/controller/listeners/comboBox/ComboJTreeListener.class
	${JAVAC} ${JAVAC_OPTIONS} $<

#-----------------------------------------------------Custom---------------------------------------------------------------------#

#------------------------------------ Dialog -------------------------------#

${BUILD}/view/custom/dialog/CustomDialogEtudiant.class : ${SRC}/view/custom/dialog/CustomDialogEtudiant.java \
														 ${BUILD}/view/custom/renderer/CustomListRenderer.class
	${JAVAC} ${JAVAC_OPTIONS} $<

#------------------------------------ Renderer -------------------------------#

${BUILD}/view/custom/renderer/CustomJTreeRenderer.class : ${SRC}/view/custom/renderer/CustomJTreeRenderer.java
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/view/custom/renderer/CustomListRenderer.class : ${SRC}/view/custom/renderer/CustomListRenderer.java
	${JAVAC} ${JAVAC_OPTIONS} $<

#------------------------------------ Tree -------------------------------#

${BUILD}/view/custom/tree/CustomJTree.class : ${SRC}/view/custom/tree/CustomJTree.java \
											${BUILD}/view/custom/renderer/CustomJTreeRenderer.class
	${JAVAC} ${JAVAC_OPTIONS} $<

#---------------------------------- PopUpMenu -------------------------------#

${BUILD}/view/custom/popupmenu/PopUpGroupe.class	:	${SRC}/view/custom/popupmenu/PopUpGroupe.java
	${JAVAC} ${JAVAC_OPTIONS} $<	

${BUILD}/view/custom/popupmenu/PopUpStudent.class	:	${SRC}/view/custom/popupmenu/PopUpStudent.java \
												${BUILD}/controller/listeners/jlist/PopUpStudentButtonListener.class
	${JAVAC} ${JAVAC_OPTIONS} $<	

#---------------------------------- Worker -------------------------------#

${BUILD}/view/custom/worker/CustomWorker.class	:	${SRC}/view/custom/worker/CustomWorker.java
	${JAVAC} ${JAVAC_OPTIONS} $<	


#------------------------------------------------------ Étudiant ------------------------------------------------------------#

${BUILD}/view/etudiant/ChangementButton.class : ${SRC}/view/etudiant/ChangementButton.java 
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/view/etudiant/ChangementPanel.class : ${SRC}/view/etudiant/ChangementPanel.java \
										  			
		${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/view/etudiant/CreateChangementPanel.class : ${SRC}/view/etudiant/CreateChangementPanel.java \
													${BUILD}/interfaces/ComboBoxGroupeInterface.class \
													${BUILD}/view/custom/renderer/CustomListRenderer.class 		
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/view/etudiant/ShowChangementPanels.class : ${SRC}/view/etudiant/ShowChangementPanels.java \
													${BUILD}/controller/listeners/buttons/ChangementButtonListener.class 		
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/view/etudiant/VoirChangements.class : ${SRC}/view/etudiant/VoirChangements.java \
											${BUILD}/controller/listeners/comboBox/MoveComboBoxListener.class \
											${BUILD}/view/etudiant/ShowChangementPanels.class \
											${BUILD}/controller/listeners/buttons/CreateChangementListener.class
	${JAVAC} ${JAVAC_OPTIONS} $<

#------------------------------------------- Panel -------------------------------------------------------------#

${BUILD}/view/panel/PanelChoix.class :  ${SRC}/view/panel/PanelChoix.java \
										${BUILD}/controller/listeners/buttons/ChoixListener.class		
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/view/panel/PanelChoixEleves.class : 	${SRC}/view/panel/PanelChoixEleves.java \
												${BUILD}/controller/listeners/buttons/ChoixEleveButton.class \
												${BUILD}/controller/listeners/jlist/ChoixEleveListListener.class
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/view/panel/PanelGroupe.class : ${SRC}/view/panel/PanelGroupe.java \
										${BUILD}/controller/listeners/jtree/GroupeTreeListener.class \
										${BUILD}/view/custom/renderer/CustomListRenderer.class \
										${BUILD}/view/custom/tree/CustomJTree.class \
										${BUILD}/controller/listeners/action/DeleteGroupeAction.class \
										${BUILD}/controller/listeners/action/AddGroupeAction.class \
										${BUILD}/controller/listeners/jtree/PopUpGroupeListener.class 
	${JAVAC} ${JAVAC_OPTIONS} $<

${BUILD}/view/panel/VoirGroupes.class : ${SRC}/view/panel/VoirGroupes.java \
										${BUILD}/view/panel/PanelGroupe.class \
										${BUILD}/controller/listeners/jtree/JTreeButtonsListener.class \
										${BUILD}/controller/listeners/buttons/AddStudentListener.class \
										${BUILD}/controller/listeners/panel/SwitchAddStudentListener.class \
										${BUILD}/view/custom/tree/CustomJTree.class			
	${JAVAC} ${JAVAC_OPTIONS} $<

#----------------------------------------- Professeur -------------------------------------------------#

${BUILD}/view/ResearchStudents.class : ${SRC}/view/ResearchStudents.java \
												${BUILD}/view/custom/renderer/CustomListRenderer.class
	${JAVAC} ${JAVAC_OPTIONS} $<
