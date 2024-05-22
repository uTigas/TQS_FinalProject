import { IonButton, IonCol, IonContent, IonHeader, IonIcon, IonPopover, IonRow, IonText, IonTitle, IonToolbar } from "@ionic/react";
import { logInOutline, logOut, person } from "ionicons/icons";
import { createContext, useContext, useEffect, useState } from "react";
import APIWrapper from "./APIWrapper";

interface HeaderProps {
  name: string;
}
export interface User {
  username: string;
  name: string;
}
const Header: React.FC<HeaderProps> = ({ name }) => {

  const [user, setUser] = useState<User | null>(null);
  useEffect(() => {
    APIWrapper.fetchAdminDetails()
      .then((response: Response | undefined) => {
        if (response && response.ok) {
          return response.json();
        } else {
          throw new Error('Failed to fetch data');
        }
      })
      .then(userData => {
        setUser(userData);
      })
      .catch(error => {
        console.error('Error:', error);
      })
  }, [])
  return (

    <IonHeader>
      <IonToolbar>
        <IonRow>
          <IonCol>
            <IonTitle>{name}</IonTitle>
          </IonCol>
          {user !== null ? (
            <IonCol className="ion-text-end">
              <IonButton id="click-trigger"><IonIcon icon={person}></IonIcon>{user.username}</IonButton>
              <IonPopover trigger="click-trigger" triggerAction="click">
                <IonContent class="ion-padding">
                  <IonText>Welcome {user.name}.</IonText>
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