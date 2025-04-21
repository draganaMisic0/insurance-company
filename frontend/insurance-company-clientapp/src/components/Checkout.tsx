import React from "react";
import api from "../axios";
import { loadStripe } from "@stripe/stripe-js";

const stripePromise = loadStripe(
  "pk_test_51RGK6UF7Mwt5mRU8Sh4LujgvxJKSURlCUziuCb5Z9Sx5qrwC2rfbEnmt9we5yoPJJv7adZxDjfZ7brnXThorbwJP00XeDG8KHp"
);

const Checkout = () => {
  const handleClick = async () => {
    try {
      const response = await api.post("/client/create-checkout-session");

      const sessionId = response.data.id;

      const stripe = await stripePromise;
      if (stripe) {
        const { error } = await stripe.redirectToCheckout({ sessionId });
        if (error) {
          console.error("Error:", error);
        }
      }
    } catch (error) {
      console.error("Error creating checkout session:", error);
    }
  };

  return (
    <button role="link" onClick={handleClick}>
      Checkout
    </button>
  );
};

export default Checkout;
