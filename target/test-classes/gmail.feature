Feature: Gmail

  Scenario: Sending an email after uploading an image file saved on my device
    Given I am on Gmail’s compose email page
    And I have filled in the “Recipients” and “Subject” fields
    When I attach an image file from my device
    And it is successfully uploaded
    Then I can send the email with the image attached

  Scenario: Sending an email after uploading an image file by providing its URL
    Given I am on Gmail’s compose email page
    And I have filled in the “Recipients” and “Subject” fields
    When I attach an image using its URL
    And it is successfully uploaded
    Then I can send the email with the image attached

  Scenario: Sending an email after removing an attached image file
    Given I am on Gmail’s compose email page
    And I have filled in the “Recipients” and “Subject” fields
    And I have attached an image file
    When I press “X”
    Then the attachment is removed
    And the email is sent without the image attachment
