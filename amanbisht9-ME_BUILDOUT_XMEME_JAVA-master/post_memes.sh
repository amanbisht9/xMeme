#!/bin/bash

# Base URL for the API endpoint
url="http://localhost:8081/memes/"

# Base name and URL for the meme
name="xyz"
image_url="https://cwod-assessment-images.s3.ap-south-1.amazonaws.com/images/130.png"

# Loop to run the curl command 100 times
for i in {1..200}
do
    # Generate a unique caption for each request
    caption="This is meme number $i"

    # Execute the curl command
    curl --location --request POST "$url" \
    --header 'Content-Type: application/json' \
    --data-raw "{
        \"name\": \"$name\",
        \"url\": \"$image_url\",
        \"caption\": \"$caption\"
    }"

    # Print a new line for readability
    echo
done
