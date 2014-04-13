namespace Com.Ericmas001.Game.Poker.GUI.Lobby
{
    partial class BlindUCBlinds
    {
        /// <summary> 
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label2 = new System.Windows.Forms.Label();
            this.lblSmallBlind = new System.Windows.Forms.Label();
            this.lblBigBlind = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold);
            this.label2.Location = new System.Drawing.Point(5, 1);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(93, 17);
            this.label2.TabIndex = 30;
            this.label2.Text = "Small Blind:";
            // 
            // lblSmallBlind
            // 
            this.lblSmallBlind.AutoSize = true;
            this.lblSmallBlind.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.lblSmallBlind.Location = new System.Drawing.Point(104, 1);
            this.lblSmallBlind.Name = "lblSmallBlind";
            this.lblSmallBlind.Size = new System.Drawing.Size(24, 17);
            this.lblSmallBlind.TabIndex = 31;
            this.lblSmallBlind.Text = "$5";
            // 
            // lblBigBlind
            // 
            this.lblBigBlind.AutoSize = true;
            this.lblBigBlind.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.lblBigBlind.Location = new System.Drawing.Point(104, 21);
            this.lblBigBlind.Name = "lblBigBlind";
            this.lblBigBlind.Size = new System.Drawing.Size(32, 17);
            this.lblBigBlind.TabIndex = 33;
            this.lblBigBlind.Text = "$10";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold);
            this.label3.Location = new System.Drawing.Point(21, 21);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(77, 17);
            this.label3.TabIndex = 32;
            this.label3.Text = "Big Blind:";
            // 
            // BlindUCBlinds
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.lblBigBlind);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.lblSmallBlind);
            this.Controls.Add(this.label2);
            this.Name = "BlindUCBlinds";
            this.Size = new System.Drawing.Size(180, 42);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label lblSmallBlind;
        private System.Windows.Forms.Label lblBigBlind;
        private System.Windows.Forms.Label label3;
    }
}
