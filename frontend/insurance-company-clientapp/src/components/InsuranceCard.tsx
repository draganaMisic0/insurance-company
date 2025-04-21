import { loadStripe } from "@stripe/stripe-js";
import api from "../axios";

const stripePromise = loadStripe(
  "pk_test_51RGK6UF7Mwt5mRU8Sh4LujgvxJKSURlCUziuCb5Z9Sx5qrwC2rfbEnmt9we5yoPJJv7adZxDjfZ7brnXThorbwJP00XeDG8KHp"
);

type InsuranceCardProps = {
  title: string;
  duration: number;
  imageUrl: string;
  price: number;
};

function InsuranceCard({
  title,
  duration,
  imageUrl,
  price,
}: InsuranceCardProps) {
  const handlePurchase = async () => {
    try {
      const response = await api.post("/create-checkout-session", {
        title,
        duration,
        price,
      });

      const sessionId = response.data.id;
      const stripe = await stripePromise;

      await stripe?.redirectToCheckout({ sessionId });
    } catch (error) {
      console.error("Failed to redirect to Stripe Checkout", error);
    }
  };
  return (
    <div className="card mx-3 mb-3" style={{ width: "18rem" }}>
      <img src={imageUrl} className="card-img-top" alt={title} />
      <div className="card-body">
        <h5 className="card-title">{title}</h5>
        <p className="card-text">Duration: {duration} years</p>

        <button onClick={handlePurchase}>Purchase</button>
      </div>
    </div>
  );
}

export default InsuranceCard;
