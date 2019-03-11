## Mahad Khan
## 260678570

Feature: Gmail

  ## Normal Flow
  Scenario: Sending an email after uploading an image file saved on my device
    Given I am on Gmail’s compose email page
    And I have filled in the Recipients and Subject fields
    When I attach an image file from my device
    And the image is successfully uploaded from device
    Then I can send the email with the image attached

  ## Normal Flow
  Scenario: Sending an image attachment as a reply
    Given I am signed in
    And I have selected to reply to an email in my inbox
    When I attach an image file
    Then I can send the reply with the image attached

  ## Normal Flow
  Scenario: Sending an image attachment to one regular and one cc email
    Given I am on Gmail’s compose email page
    And I have filled in the Recipient and cc field
    And I have filled in the subject field
    When I attach an image file from my device
    And the image is successfully uploaded from device
    Then I can send the email with the image attached

  ## Alternate Flow
  Scenario: Sending an email with an image attachment and without a Subject
    Given I am on Gmail’s compose email page
    And I have filled in the Recipient field
    When I attempt to send an image file from my device
    And I accept the warning
    Then the email will be sent

  ## Error Flow
  Scenario: Sending an email after removing an attached image file
    Given I am on Gmail’s compose email page
    And I have filled in the Recipients and Subject fields
    And I have attached an image file
    When I press X next to the uploaded attachment
    Then the email is sent without the image attachment

