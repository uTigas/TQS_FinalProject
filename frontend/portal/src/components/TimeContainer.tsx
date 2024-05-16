import { IonButton, IonCol, IonGrid, IonIcon, IonRow, IonSelect, IonSelectOption, IonTitle } from "@ionic/react"
import { ConnectionProp } from "./Header";
import { search } from "ionicons/icons";
export interface TimeContainerProps {
  origin: string;
  destination: string;
}

const TimeContainer: React.FC<TimeContainerProps> = (() => {
    let connections: ConnectionProp[] = [];
    let possible: ConnectionProp[] = [];
    let selectedOrigin = "";
    let selectedDestination = "";

    const updatePossibilities = () => {
        
    } 
    return (
        <IonGrid className='ion-padding'>
              <IonRow>
                <IonTitle>Where would you like to go?</IonTitle>
              </IonRow>
              <form>
                <IonRow className='ion-padding'>
                  <IonCol>
                    <IonSelect id='selectOrigin' label="Select Origin" labelPlacement="floating" fill="outline" interface="popover" onIonChange={() => updatePossibilities()}>
                      {possible.map((con) => <IonSelectOption>{con.origin}</IonSelectOption>)}
                    </IonSelect>
                  </IonCol>
                  <IonCol>
                    <IonSelect id='selectDestination' label="Select Destination" labelPlacement="floating" fill="outline" interface="popover" onIonChange={() => updatePossibilities()}>
                      {possible.map((con) => <IonSelectOption>{con.destination}</IonSelectOption>)}
                    </IonSelect>
                  </IonCol>
                  <IonCol size='1'>
                    <IonButton size='small' shape='round' color='warning'>Search <IonIcon icon={search}></IonIcon></IonButton>
                  </IonCol>
                </IonRow>
              </form>
        </IonGrid>
    );
})

export default TimeContainer;