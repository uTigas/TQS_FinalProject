import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Tab1.css';
import Header from '../components/Header';

const Tab1: React.FC = () => {
  return (
    <IonPage>
      <Header name='Homepage'/>
      <IonContent fullscreen>
        <IonHeader collapse="condense">
          <IonToolbar>
            <IonTitle size="large">Admin Dashboard</IonTitle>
          </IonToolbar>
        </IonHeader>
        <ExploreContainer name="Admin Dashboard" />
      </IonContent>
    </IonPage>
  );
};

export default Tab1;
