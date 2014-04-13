namespace Com.Ericmas001.Game.Poker.GUI.Lobby
{
    partial class BlindUCAnte
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
            this.nudAnte = new System.Windows.Forms.NumericUpDown();
            this.lblAnte = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.nudAnte)).BeginInit();
            this.SuspendLayout();
            // 
            // nudBlind
            // 
            this.nudAnte.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.nudAnte.Increment = new decimal(new int[] {
            100,
            0,
            0,
            0});
            this.nudAnte.Location = new System.Drawing.Point(3, 3);
            this.nudAnte.Maximum = new decimal(new int[] {
            1000,
            0,
            0,
            0});
            this.nudAnte.Minimum = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.nudAnte.Name = "nudBlind";
            this.nudAnte.Size = new System.Drawing.Size(52, 23);
            this.nudAnte.TabIndex = 31;
            this.nudAnte.Value = new decimal(new int[] {
            10,
            0,
            0,
            0});
            // 
            // lblBlind
            // 
            this.lblAnte.AutoSize = true;
            this.lblAnte.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.lblAnte.Location = new System.Drawing.Point(61, 5);
            this.lblAnte.Name = "lblBlind";
            this.lblAnte.Size = new System.Drawing.Size(73, 17);
            this.lblAnte.TabIndex = 30;
            this.lblAnte.Text = "(Ante)";
            // 
            // BlindUCBlinds
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.nudAnte);
            this.Controls.Add(this.lblAnte);
            this.Name = "BlindUCBlinds";
            this.Size = new System.Drawing.Size(146, 33);
            ((System.ComponentModel.ISupportInitialize)(this.nudAnte)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblAnte;
        internal System.Windows.Forms.NumericUpDown nudAnte;
    }
}
