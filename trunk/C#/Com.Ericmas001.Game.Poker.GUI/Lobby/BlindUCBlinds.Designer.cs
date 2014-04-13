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
            this.nudBlind = new System.Windows.Forms.NumericUpDown();
            this.lblBlind = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.nudBlind)).BeginInit();
            this.SuspendLayout();
            // 
            // nudBlind
            // 
            this.nudBlind.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.nudBlind.Increment = new decimal(new int[] {
            100,
            0,
            0,
            0});
            this.nudBlind.Location = new System.Drawing.Point(3, 3);
            this.nudBlind.Maximum = new decimal(new int[] {
            1000,
            0,
            0,
            0});
            this.nudBlind.Minimum = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.nudBlind.Name = "nudBlind";
            this.nudBlind.Size = new System.Drawing.Size(52, 23);
            this.nudBlind.TabIndex = 31;
            this.nudBlind.Value = new decimal(new int[] {
            10,
            0,
            0,
            0});
            // 
            // lblBlind
            // 
            this.lblBlind.AutoSize = true;
            this.lblBlind.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.lblBlind.Location = new System.Drawing.Point(61, 5);
            this.lblBlind.Name = "lblBlind";
            this.lblBlind.Size = new System.Drawing.Size(73, 17);
            this.lblBlind.TabIndex = 30;
            this.lblBlind.Text = "(Big Blind)";
            // 
            // BlindUCBlinds
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.nudBlind);
            this.Controls.Add(this.lblBlind);
            this.Name = "BlindUCBlinds";
            this.Size = new System.Drawing.Size(146, 33);
            ((System.ComponentModel.ISupportInitialize)(this.nudBlind)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblBlind;
        internal System.Windows.Forms.NumericUpDown nudBlind;
    }
}
