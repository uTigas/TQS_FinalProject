import React from 'react';
import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar, IonGrid, IonRow, IonCol } from '@ionic/react';
import DashboardCard from '../components/DashboardCard';

const Tab1: React.FC = () => {

  const cardsData = [
    { title: "Stations", subtitle: "Manage stations here", imageSrc: "../../assets/station.jpg"},
    { title: "Trains", subtitle: "Manage trains here", imageSrc: "../../assets/train.jpeg" },
    { title: "Connections", subtitle: "Manage connections here", imageSrc: "../../assets/connection.jpeg" },
  ];

  const renderCards = () => {
    return cardsData.map((data, index) => (
      <IonCol size="12" sizeMd="4" key={index}>
        <DashboardCard data={data} />
      </IonCol>
    ));
  };

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Admin Dashboard</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <IonHeader collapse="condense">
          <IonToolbar>
            <IonTitle size="large">Admin Dashboard</IonTitle>
          </IonToolbar>
        </IonHeader>
        <IonGrid>
          <IonRow>
            {renderCards()}
          </IonRow>
        </IonGrid>
      </IonContent>
    </IonPage>
  );
};

export default Tab1;
