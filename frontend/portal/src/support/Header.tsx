import { IonButton, IonCol, IonContent, IonHeader, IonIcon, IonPopover, IonRow, IonText, IonTitle, IonToolbar } from "@ionic/react";
import { logInOutline, logOut, person } from "ionicons/icons";
import { useContext, useEffect, useState } from "react";
import APIWrapper from "../components/APIWrapper";
import { User, HeaderProps, SharedVariablesContext } from "./Variables";

const Header: React.FC<HeaderProps> = ({ name }) => {
    const {loggedUser, setLoggedUser}  = useContext(SharedVariablesContext);
    
    useEffect(() => {
        APIWrapper.fetchUserDetails()
            .then((response: Response | undefined) => {
                if (response && response.ok) {
                    return response.json();
                } else {
                    throw new Error('Failed to fetch data');
                }
            })
            .then(userData => {
                const newUser : User = {username: userData.username, name: userData.name, role: userData.role }
                setLoggedUser(newUser)
                console.log(loggedUser)
            })
            .catch(error => {
                console.error('Error:', error.message);
                setLoggedUser(null)
            })
    }, [])

    useEffect(() => {
        console.log("User was refreshed")
        console.log(loggedUser);
    }, [loggedUser]); 
    
    return (

        <IonHeader>
            <IonToolbar>
                <IonRow>
                    <IonCol>
                        <IonTitle>{name}</IonTitle>
                    </IonCol>
                    {loggedUser !== null ? (
                        <IonCol className="ion-text-end">
                            <IonButton id="click-trigger"><IonIcon icon={person}></IonIcon>{loggedUser.username}</IonButton>
                            <IonPopover trigger="click-trigger" triggerAction="click">
                                <IonContent class="ion-padding">
                                    <IonText>Welcome {loggedUser.name}.</IonText>
                                    <IonButton color={"danger"} href={APIWrapper.backendURI + "auth/logout"}><IonIcon icon={logOut}></IonIcon>Logout</IonButton>
                                </IonContent>
                            </IonPopover>
                        </IonCol>
                    ) : (
                        <IonCol className="ion-text-end">
                            <IonButton id="login" className="normal-button" color={"success"} href={APIWrapper.backendURI + "auth/login"}>Login<IonIcon icon={logInOutline}></IonIcon></IonButton>
                        </IonCol>

                    )}
                </IonRow>
            </IonToolbar>
        </IonHeader>
    );
};

export default Header;