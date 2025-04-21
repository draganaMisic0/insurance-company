import axios from "./../axios";
import { useEffect, useState } from "react";
import InsuranceCard from "./InsuranceCard";
import imageUrl from "./../assets/logo.jpg";

type Insurance = {
  id: number;
  pdfUrl: string;
  insuranceType: {
    id: number;
    name: string;
    duration: number;
    price: number;
  };
};

function InsuranceList() {
  const [insurances, setInsurances] = useState<Insurance[]>([]);

  useEffect(() => {
    axios
      .get<Insurance[]>("https://localhost:8443/client/policy-list")
      .then((response) => {
        console.log("it's fetching");
        setInsurances(response.data);
      })
      .catch((error) => console.error("Failed to fetch insurances", error));
  }, []);

  return (
    <div className="d-flex flex-wrap gap-0">
      {insurances.map((insurance) => (
        <InsuranceCard
          key={insurance.id}
          title={insurance.insuranceType.name}
          duration={insurance.insuranceType.duration}
          imageUrl={imageUrl}
          price={insurance.insuranceType.price}
        />
      ))}
    </div>
  );
}

export default InsuranceList;
