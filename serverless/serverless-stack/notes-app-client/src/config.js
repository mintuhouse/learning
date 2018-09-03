export default {
  MAX_ATTACHMENT_SIZE: 5000000,
  s3: {
    REGION: "ap-south-1",
    BUCKET: "astarlive-learn-serverless-stack",
  },
  apiGateway: {
    REGION: "ap-south-1",
    URL: "https://6y7l22j2ti.execute-api.ap-south-1.amazonaws.com/prod",
  },
  cognito: {
    REGION: "ap-south-1",
    USER_POOL_ID: "ap-south-1_lIFveWFyn",
    APP_CLIENT_ID: "40kt3hh43v664iu1jrukou5nt0",
    IDENTITY_POOL_ID: "ap-south-1:d41f9b64-fc85-49ec-8e1a-ad944cb53562",
  },
};
