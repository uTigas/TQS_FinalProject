import { IonBackdrop, IonButton, IonCol, IonContent, IonDatetime, IonDatetimeButton, IonGrid, IonHeader, IonIcon, IonInput, IonItem, IonModal, IonPage, IonRow, IonSelect, IonSelectOption, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Tab1.css';
import { pin, search } from 'ionicons/icons';
import Header from '../components/Header';
import React from 'react';

const Tab1: React.FC = () => {
  let connections = ["Empty Array"];
  let connections = [];
  let connections = [];

  const updatePossibilities = () => {
    
  }  
  
  return (
    <IonPage>
      <Header name='Homepage'/>
      <IonContent fullscreen>
        <IonGrid className='ion-padding'>
          <IonRow>
            <IonTitle>Where would you like to go?</IonTitle>
          </IonRow>
          <form>
            <IonRow className='ion-padding'>
              <IonCol>
                <IonSelect label="Select Origin" labelPlacement="floating" fill="outline" interface="popover" onIonChange={() => updatePossibilities()}>
                  {connections.map((con) => <IonSelectOption>Apple</IonSelectOption>)}
                </IonSelect>
              </IonCol>
              <IonCol>
                <IonInput name='destination' placeholder='Destination'></IonInput>
              </IonCol>
              <IonCol size='1'>
                <IonButton size='small' shape='round' color='warning'>Search <IonIcon icon={search}></IonIcon></IonButton>
              </IonCol>
            </IonRow>
          </form>
        </IonGrid>
      </IonContent>
    </IonPage>
  );
};

export default Tab1;
