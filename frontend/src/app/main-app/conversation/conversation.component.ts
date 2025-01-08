import { animate, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';



interface message {
  id: number;
  receiver_id: number;
  sender_id: number;
  message: string;
  timestamp: number;
}

@Component({
  selector: 'app-conversation',
  imports: [FormsModule],
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.css',
  animations: [
    trigger("sendButton", [
      transition(":enter", [
        style({ opacity: 0 }),
        animate("0.3s ease-in", style({ opacity: 1 }))
      ]),
      transition(":leave", [
        style({ opacity: 1 }),
        animate("0.3s ease-out", style({ opacity: 0 }))
      ])
    ]
    )
  ]
})


export class ConversationComponent implements OnInit {

  message: string = "";
  user_id: number = 1;
  isLastMessage!: boolean;
  messages: message[] = [
    {
      id: 1,
      receiver_id: 1,
      sender_id: 2,
      message: "Hello",
      timestamp: 123456709

    },
    {
      id: 2,
      receiver_id: 2,
      sender_id: 1,
      message: "Hi",
      timestamp: 123456710
    },
    {
      id: 3,
      receiver_id: 1,
      sender_id: 2,
      message: "How are you?",
      timestamp: 123456711
    },
    {
      id: 4,
      receiver_id: 2,
      sender_id: 1,
      message: "I am fine",
      timestamp: 123456712
    },
    {
      id: 5,
      receiver_id: 1,
      sender_id: 2,
      message: "Good to hear",
      timestamp: 123456713
    },
    {
      id: 6,
      receiver_id: 1,
      sender_id: 2,
      message: "What you doing?",
      timestamp: 123456714
    },
    {
      id: 7,
      receiver_id: 1,
      sender_id: 2,
      message: "xd",
      timestamp: 123456720
    },

    {
      id: 8,
      receiver_id: 2,
      sender_id: 1,
      message: "I am fine",
      timestamp: 123456721
    },
    {
      id: 9,
      receiver_id: 1,
      sender_id: 2,
      message: "xd",
      timestamp: 123456722
    }
  ];

  ngOnInit(): void {
    this.messages.sort((a, b) => a.timestamp - b.timestamp);
  }

  isLastMessageFromSender(messageIndex: number): boolean {
    if (messageIndex === this.messages.length - 1) return true;
    return this.messages[messageIndex].sender_id !== this.messages[messageIndex + 1].sender_id;
  }


}
