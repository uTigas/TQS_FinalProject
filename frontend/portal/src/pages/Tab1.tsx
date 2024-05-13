import { IonBackdrop, IonButton, IonCol, IonContent, IonDatetime, IonDatetimeButton, IonGrid, IonHeader, IonIcon, IonInput, IonItem, IonModal, IonPage, IonRow, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Tab1.css';
import { pin, search } from 'ionicons/icons';
import Header from '../components/Header';

const Tab1: React.FC = () => {
  return (
    <IonPage>
      <Header name='Homepage'/>
      <IonContent fullscreen>
        <IonGrid className='ion-padding'>
          <IonRow>
            <IonTitle>Horários e Preços:</IonTitle>
          </IonRow>
          <form>
            <IonRow>
              <IonCol>
                <IonInput name='origin' placeholder='Origem'></IonInput>
              </IonCol>
              <IonCol>
                <IonDatetimeButton datetime="date"></IonDatetimeButton>
                <IonModal keepContentsMounted={true}>
                  <IonDatetime id="date" presentation="date"></IonDatetime>
                </IonModal>
              </IonCol>
              <IonCol>
                <IonInput name='destination' placeholder='Destino'></IonInput>
              </IonCol>
              <IonCol>
                <IonDatetimeButton datetime="returnDate"></IonDatetimeButton>
                <IonModal keepContentsMounted={true}>
                    <IonDatetime id="returnDate" presentation="date"></IonDatetime>
                  </IonModal>
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
