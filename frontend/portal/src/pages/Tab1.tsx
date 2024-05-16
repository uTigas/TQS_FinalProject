import { IonBackdrop, IonButton, IonCol, IonContent, IonDatetime, IonDatetimeButton, IonGrid, IonHeader, IonIcon, IonInput, IonItem, IonModal, IonPage, IonRow, IonSearchbar, IonSelect, IonSelectOption, IonTitle, IonToolbar } from '@ionic/react';
import './Tab1.css';
import React from 'react';
import Header, { ConnectionProp } from '../components/Header';
import SelectContainer from '../components/SelectContainer';
import TimeContainer from '../components/TimeContainer';
import { arrowBackCircle, arrowBackOutline, arrowForwardCircle } from 'ionicons/icons';

export let container = <SelectContainer />;
export let connections: ConnectionProp[] = [];
export let possible: ConnectionProp[] = [];
export let selectedOrigin = "";
export let selectedDestination = "";

const Tab1: React.FC = () => { 
  
  const showTimeContainer = () => {
    container = <TimeContainer origin={selectedOrigin} destination={selectedDestination} />;
}  

  return (
    <IonPage>
      <Header name='Homepage'/>
      <IonContent fullscreen>
        <IonRow className='ion-padding'>
          <IonCol size='1'>
            <IonButton onClick={() => showTimeContainer()} id='prevContainer'>
                <IonIcon icon={arrowBackCircle} size="large"></IonIcon>
            </IonButton>
          </IonCol>
          <IonCol>
            {container}
          </IonCol>
          <IonCol size='1'>
            <IonButton onClick={() => showTimeContainer()} id='nextContainer'>
                <IonIcon icon={arrowForwardCircle} size="large"></IonIcon>
            </IonButton>
          </IonCol>
        </IonRow>
      </IonContent> 
    </IonPage>
  );
};

export default Tab1;
