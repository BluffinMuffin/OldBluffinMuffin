namespace Com.Ericmas001.Game.Poker.GUI.Lobby
{
    partial class BlindUcAnte
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
            this.lblAnte = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold);
            this.label2.Location = new System.Drawing.Point(5, 2);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(51, 17);
            this.label2.TabIndex = 30;
            this.label2.Text = "Ante: ";
            // 
            // lblAnte
            // 
            this.lblAnte.AutoSize = true;
            this.lblAnte.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F);
            this.lblAnte.Location = new System.Drawing.Point(62, 2);
            this.lblAnte.Name = "lblAnte";
            this.lblAnte.Size = new System.Drawing.Size(32, 17);
            this.lblAnte.TabIndex = 31;
            this.lblAnte.Text = "$10";
            // 
            // BlindUCAnte
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
            this.Controls.Add(this.lblAnte);
            this.Controls.Add(this.label2);
            this.Name = "BlindUcAnte";
            this.Size = new System.Drawing.Size(146, 33);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label lblAnte;
    }
}
