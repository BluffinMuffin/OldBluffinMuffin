namespace Com.Ericmas001.Game.BluffinMuffin.Client.Menu
{
    partial class TrainingParmsForm
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

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(TrainingParmsForm));
            this.btnTraining = new VIBlend.WinForms.Controls.vButton();
            this.txtUsername = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // btnTraining
            // 
            this.btnTraining.AllowAnimations = true;
            this.btnTraining.BackColor = System.Drawing.Color.Transparent;
            this.btnTraining.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnTraining.Image = global::Com.Ericmas001.Game.BluffinMuffin.Client.Properties.Resources.learn;
            this.btnTraining.Location = new System.Drawing.Point(14, 74);
            this.btnTraining.Name = "btnTraining";
            this.btnTraining.RoundedCornersMask = ((byte)(15));
            this.btnTraining.Size = new System.Drawing.Size(353, 65);
            this.btnTraining.TabIndex = 0;
            this.btnTraining.Text = "Start Training";
            this.btnTraining.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnTraining.UseVisualStyleBackColor = false;
            this.btnTraining.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROBLUE;
            this.btnTraining.Click += new System.EventHandler(this.btnTraining_Click);
            // 
            // txtUsername
            // 
            this.txtUsername.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtUsername.Location = new System.Drawing.Point(14, 36);
            this.txtUsername.Name = "txtUsername";
            this.txtUsername.Size = new System.Drawing.Size(353, 23);
            this.txtUsername.TabIndex = 18;
            this.txtUsername.Text = "Player";
            this.txtUsername.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.txtUsername.TextChanged += new System.EventHandler(this.txtUsername_TextChanged);
            // 
            // label2
            // 
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(14, 9);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(353, 19);
            this.label2.TabIndex = 19;
            this.label2.Text = "Player Name:";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // TrainingParmsForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(381, 151);
            this.Controls.Add(this.txtUsername);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.btnTraining);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "TrainingParmsForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Training ~ Bluffin Muffin Client";
            this.ResumeLayout(false);
            this.PerformLayout();

        }
        private VIBlend.WinForms.Controls.vButton btnTraining;
        private System.Windows.Forms.TextBox txtUsername;
        private System.Windows.Forms.Label label2;
        #endregion
    }
}