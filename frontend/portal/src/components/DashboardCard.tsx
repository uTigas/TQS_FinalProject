import React from 'react';
import { IonCard, IonCardHeader, IonCardSubtitle, IonCardTitle } from '@ionic/react';

interface CardData {
  title: string;
  subtitle: string;
  imageSrc: string;
}

const DashboardCard: React.FC<{ data: CardData }> = ({ data }) => {
    const { title, subtitle, imageSrc } = data;
    const lowerCaseTitle = title.toLowerCase();

    const goToManagementPage = () => {
        window.location.href = `/admin/${lowerCaseTitle}`;
    };
  
    return (
      <IonCard onClick={goToManagementPage}>
        <img src={imageSrc} alt={title} />
        <IonCardHeader>
          <IonCardTitle>{title}</IonCardTitle>
          <IonCardSubtitle>{subtitle}</IonCardSubtitle>
        </IonCardHeader>
      </IonCard>
    );
  };

export default DashboardCard;
