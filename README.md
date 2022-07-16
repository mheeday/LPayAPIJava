# LPayAPIJava
Implement payment splitting API using SpringBoot/Java

Checkout comprehensive description @ https://github.com/mheeday/PayAPI

You can send *POST* request to this endpoint https://stark-falls-62497.herokuapp.com/split-payments/compute

Kindly use the sample request body below, I haven't implemented error checking yet

Sample Payloads:

Request:
{
    "ID": 1308,
    "Amount": 12580,
    "Currency": "NGN",
    "CustomerEmail": "anon8@customers.io",
    "SplitInfo": [
        {
            "SplitType": "FLAT",
            "SplitValue": 45,
            "SplitEntityId": "LNPYACC0019"
        },
        {
            "SplitType": "RATIO",
            "SplitValue": 3,
            "SplitEntityId": "LNPYACC0011"
        },
        {
            "SplitType": "PERCENTAGE",
            "SplitValue": 3,
            "SplitEntityId": "LNPYACC0015"
        }
    ]
}
Response: 
{
    "ID": 1308,
    "Balance": 0,
    "SplitBreakdown": [
        {
            "SplitEntityId": "LNPYACC0019",
            "Amount": 45
        },
        {
            "SplitEntityId": "LNPYACC0015",
            "Amount": 376.05
        },
        {
            "SplitEntityId": "LNPYACC0011",
            "Amount": 12158.95
        }
    ],
    "ratioCount": 3
}

------------------------------------
Request:
{
    "ID": 13092,
    "Amount": 4500,
    "Currency": "NGN",
    "CustomerEmail": "anon8@customers.io",
    "SplitInfo": [
        {
            "SplitType": "FLAT",
            "SplitValue": 450,
            "SplitEntityId": "LNPYACC0019"
        },
        {
            "SplitType": "RATIO",
            "SplitValue": 3,
            "SplitEntityId": "LNPYACC0011"
        },
        {
            "SplitType": "PERCENTAGE",
            "SplitValue": 3,
            "SplitEntityId": "LNPYACC0015"
        },
        {
            "SplitType": "RATIO",
            "SplitValue": 2,
            "SplitEntityId": "LNPYACC0016"
        },
        {
            "SplitType": "FLAT",
            "SplitValue": 2450,
            "SplitEntityId": "LNPYACC0029"
        },
        {
            "SplitType": "PERCENTAGE",
            "SplitValue": 10,
            "SplitEntityId": "LNPYACC0215"
        },
    ]
}
Response:
{
    "ID": 13092,
    "Balance": 0,
    "SplitBreakdown": [
        {
            "SplitEntityId": "LNPYACC0019",
            "Amount": 450
        },
        {
            "SplitEntityId": "LNPYACC0029",
            "Amount": 2450
        },
        {
            "SplitEntityId": "LNPYACC0015",
            "Amount": 48.0
        },
        {
            "SplitEntityId": "LNPYACC0215",
            "Amount": 155.20000000000002
        },
        {
            "SplitEntityId": "LNPYACC0011",
            "Amount": 838.0799999999999
        },
        {
            "SplitEntityId": "LNPYACC0016",
            "Amount": 558.72
        }
    ],
    "ratioCount": 5
}